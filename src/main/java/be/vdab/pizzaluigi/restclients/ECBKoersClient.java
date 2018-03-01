package be.vdab.pizzaluigi.restclients;
import be.vdab.pizzaluigi.exceptions.KoersClientException;
import java.io.InputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
//@Primary
@Qualifier("ECB")
class ECBKoersClient implements KoersClient {
	private final static Logger LOGGER = LoggerFactory.getLogger(ECBKoersClient.class);
	private URL url;
	ECBKoersClient() {
		try {
			this.url = new URL("http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml");
		} catch (MalformedURLException ex) {
			String fout = "ECB URL is verkeerd: " + url;
			LOGGER.error(fout, ex);
			throw new KoersClientException(fout);
		}
	}
	@Override
	public BigDecimal getDollarKoers() {
		/*
		 * In het try-blok hieronder gebruik je een XMLStreamReader object om XML te lezen.
		 * Om dit object te maken heb je een XMLInputFactory nodig.
		 * Dit is een toepassing van het factory design pattern.
		 * Je maakt het XMLInputFactory object op zijn beurt met de static method newInstance.
		 */
		XMLInputFactory factory = XMLInputFactory.newInstance();
		try ( InputStream stream = url.openStream() ) {						// (throws IOException)
			/*
			 * Maakt een XMLStreamReader object.
			 * Je leest met zo'n object sequentieel (één per één) de onderdelen van een XML bron:
			 * begintags, eindtags, commentaar, ...
			 */
			XMLStreamReader reader = factory.createXMLStreamReader(stream);	// (throws XMLStreamException)
			try {
				/*
				 * De method hasNext geeft true terug zolang je nog onderdelen uit het XML bestand kan lezen.
				 * Deze method geeft false terug bij het einde van het bestand.
				 */
				while (reader.hasNext()) {
					/*
					 * Leest met de method next een volgend onderdeel uit de XML bron.
					 * Deze method geeft je een int terug met het soort onderdeel.
					 * Als deze int gelijk is aan de constante START_ELEMENT heb je een begintag gelezen.
					 */
					if (reader.next() == XMLStreamConstants.START_ELEMENT
							/*
							 * Controleert of de tag een attribuut heeft met de naam USD.
							 * Je zou de parameterwaarde null vervangen door een namespace als de XML bron met een namespace
							 * geassocieerd is. Dit is hier niet het geval.
							 */
							&& "USD".equals(reader.getAttributeValue(null, "currency"))) {
						LOGGER.info("koers gelezen via ECB");
						// Leest de koers van de dollar in het attribuut rate.
						return new BigDecimal(reader.getAttributeValue(null, "rate"));	// (throws NumberFormatException)
					}
				}
				String fout = "XML van ECB bevat geen USD";
				LOGGER.error(fout);
				throw new KoersClientException(fout);
			} finally {
				reader.close();
			}
		} catch (IOException | XMLStreamException | NumberFormatException ex) {
			/*
			 * Als je hier komt is er ofwel een exception opgetreden, 
			 * of je hebt de volledige XML bron doorlezen en de koers van de dollar niet tegengekomen.
			 */
			String fout = "kan koers niet lezen via ECB";
			LOGGER.error(fout, ex);
			throw new KoersClientException(fout);
		}
	}
}

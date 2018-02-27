package be.vdab.pizzaluigi.restclients;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

// Importeer Logger en LoggerFactory uit org.slf4j
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import be.vdab.pizzaluigi.exceptions.KoersClientException;
/*
 * Als je @Component voor een class tikt, maakt Spring bij het starten van de website een bean (object)
 * van deze class en houdt deze bean bij zolang de website draait.
 * Het is deze bean die Spring via de constructor zal injecteren in de EuroService bean.
 */
@Component
class FixerKoersClient implements KoersClient {
	/*
	 * Als er een fout optreedt, wil je informatie over die fout wegschrijven naar de console,
	 * een bestand, ... Hiervoor bestaat de class Logger.
	 * Standaard schrijft de Logger de foutinformatie naar de console.
	 * Als je logging.file=/logging/log.txt toevoegt aan application.properties,
	 * schrijft Spring de foutinformatie ook naar het bestand log.txt in de map logging
	 * in de root van je hard disk.
	 */
	private static final Logger LOGGER = 
			// De static method getLogger geeft je een Logger object. Je geeft aan getLogger de huidige class mee.
			LoggerFactory.getLogger(FixerKoersClient.class);
	private URL url;
	FixerKoersClient() {
		try {
			url = new URL("https://api.fixer.io/latest?symbols=USD");
		} catch(MalformedURLException ex) {
			String fout = "Fixer URL is verkeerd: " + url;
			/*
			 * Je schrijft foutinformatie weg.
			 * Je geeft als tweede parameter de opgetreden exception mee.
			 * De Logger schrijft dan ook weg op welk lijnnummer van welke source de exception optrad.
			 */
			LOGGER.error(fout, ex);
			throw new KoersClientException(fout);
		}
	}
	@Override
	public BigDecimal getDollarKoers() {
		try (Scanner scanner = new Scanner(url.openStream())) {
			String lijn = scanner.nextLine();
			int beginPositieKoers = lijn.indexOf("USD") + 5;
			int accoladePosite = lijn.indexOf("}", beginPositieKoers);
			return new BigDecimal(lijn.substring(beginPositieKoers, accoladePosite));
		} catch (IOException | NumberFormatException ex) {
			String fout = "kan koers niet lezen via Fixer";
			LOGGER.error(fout, ex);
			throw new KoersClientException(fout);
		}
	}

}

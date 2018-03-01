package be.vdab.pizzaluigi.services;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import be.vdab.pizzaluigi.restclients.KoersClient;
/*
 * Als je @Service tikt voor een class, maakt Spring bij het starten van de website een bean (object)
 * van deze class en houdt deze bean bij zolang de website in uitvoering is.
 * Dit is vergelijkbaar met @Controller voor een class.
 */
@Service
class DefaultEuroService implements EuroService {
	/*
	 * De variabele kan verwijzen naar elk object dat de interface KoersClient implementeert.
	 * Dit kan een FixerKoersClient object zijn als de code uitgevoerd wordt op de website,
	 * of een dummy object aangemaakt met Mockito als de code uitgevoerd wordt in een test.
	 * Je maakt de variabele final. Je garandeert zo dat je deze variabele in 
	 * een andere method dan de constructor niet kan wijzigen en per ongeluk op null zou plaatsen.
	 */
	private final KoersClient koersClient;
	/*
	 * De constructor krijgt een object geïnjecteerd dat de interface KoersClient implementeert.
	 * Bij het unit testen roep je deze constructor op en injecteer je een dummy die je maakt met Mockito.
	 * Als de code uitgevoerd wordt in de website roept Spring deze constructor op.
	 * Spring ziet dat hij een object moet meegeven dat de interface KoersClinet implemneteert.
	 * Spring zoekt tussen alle beans een bean die deze interface implemneteert.
	 * Als Spring zo'n bean vindt, geeft hij deze bean mee aan de constructor.
	 * Dit heet dependency injection.
	 * Als Spring zo'n bean niet vindt, krijg je een exception. 
	 * We zorgen er straks voor dat Spring een bean ter beschikking heeft gebaseerd op de class FixerKoersClient.
	 */
//	DefaultEuroService(KoersClient koersClient) {
	/*
	 * Je tikt @Qualifier vóór de te injecteren parameter. Je geeft de string Fixer mee.
	 * Spring injecteert de bean van de class die de interface KoersClient implementeert
	 * én voorzien is van de @Qualifier("Fixer"). Dit is de bean van de class FixerKoersClient.
	 */
	DefaultEuroService(@Qualifier("Fixer") KoersClient koersClient) {
		this.koersClient = koersClient;
	}
	/* (non-Javadoc)
	 * @see be.vdab.pizzaluigi.services.EuroService#naarDollar(java.math.BigDecimal)
	 */
	@Override
	public BigDecimal naarDollar(BigDecimal euro) {
		return euro.multiply(koersClient.getDollarKoers()).setScale(2, RoundingMode.HALF_UP);
	}
}

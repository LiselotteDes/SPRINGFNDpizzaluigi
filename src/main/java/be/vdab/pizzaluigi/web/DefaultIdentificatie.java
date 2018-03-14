package be.vdab.pizzaluigi.web;
import java.io.Serializable;
import javax.validation.constraints.Email;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
// De data die je wil onthouden in de session moet een Spring bean zijn.
@Component
/*
 * Standaard maakt Spring één bean bij het starten van de applicatie. Alle gebruikers delen die ene bean.
 * Dit is niet wat je van een session verwacht.
 * Met @SessionScope maakt Spring een bean per gebruiker die de website bezoekt en houdt die bean bij in de session van die gebruiker.
 */
@SessionScope
// De data in de session moet serializable zijn.
class DefaultIdentificatie implements Serializable, Identificatie {
	private static final long serialVersionUID = 1L;
	@Email
	private String emailAdres;
	@Override
	public String getEmailAdres() {
		return emailAdres;
	}
	@Override
	public void setEmailAdres(String adres) {
		this.emailAdres = adres;
	}
}

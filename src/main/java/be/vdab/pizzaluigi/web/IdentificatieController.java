package be.vdab.pizzaluigi.web;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
// De gebruiker moet zijn email adres kunnen intikken. Daartoe voeg je deze controller toe.
@Controller
@RequestMapping("identificatie")
class IdentificatieController {
	private static final String VIEW = "identificatie";
	private static final String REDIRECT_NA_SUBMIT = "redirect:/";
	private final Identificatie identificatie;
	// Je injecteert de Spring bean die toegang geeft tot de session data met het email adres van de huidige gebruiker.
	IdentificatieController(Identificatie identificatie) {
		this.identificatie = identificatie;
	}
	@GetMapping
	ModelAndView identificatie() {
		/*
		 * Je geeft het object met het email adres door aan de JSP. Je gebruikt het daar als form object.
		 * Er zijn 2 mogelijkheden. 
		 * 1° mogelijkheid: De gebruiker surft voor de eerste keer naar de pagina.
		 * De webserver heeft dan voor die gebruiker een nieuwe session gemaakt. Deze session bevat een nieuw aangemaakt DefaultIdentificatie object.
		 * 2° mogelijkheid: De gebruiker surft voor een volgende keer naar de pagina.
		 * De webserver heeft dan de session van de huidige gebruiker opgehaald uit zijn RAM geheugen.
		 * Deze session bevat het DefaultIdentificatie object van de vorige requests van die gebruiker. Je kan dus uit dit object het email adres
		 * lezen dat de gebruiker bij de vorige requests intikte.
		 */
		return new ModelAndView(VIEW, "identificatie", identificatie);
	}
	/*
	 * De method die volgt op deze annotatie zal het submitten van de form verwerken.
	 * Hierbij WIJZIGT data in de session van de gebruiker. Je submit daarom de form met method="post" ipv met method="get".
	 */
	@PostMapping
	/*
	 * Deze method verwerkt de request nadat de gebruiker de form submit.
	 * Spring heeft een nieuw DefaultIdentificatie object aangemaakt en opgevuld met het adres dat de gebruiker intikte.
	 * Je valideert dit object.
	 */
	String identificatie(@Valid DefaultIdentificatie identificatie, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return VIEW;
		}
		/*
		 * Je brengt het ingetikte adres over naar het DefaultIdentificatie object in de session van de gebruiker.
		 * Zo is het ter beschikking bij volgende requests van de gebruiker.
		 */
		this.identificatie.setEmailAdres(identificatie.getEmailAdres());
		return REDIRECT_NA_SUBMIT;
	}
}

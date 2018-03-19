package be.vdab.pizzaluigi.web;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.pizzaluigi.valueobjects.Adres;
import be.vdab.pizzaluigi.valueobjects.DatumTijd;
import be.vdab.pizzaluigi.valueobjects.Persoon;
// Je tikt @RestController voor een class die dient als controller
//@RestController
// Als een controller samenwerkt met een JSP om een browser request te verwerken, tik je voor de class @Controller ipv @RestController.
@Controller
/*
 * Je associeert met @RequestMapping de controller met een URL in je website.
 * Dit is hier de URL die staat voor de welkompagina.
 */
@RequestMapping("/")
/*
 * De naam van een controller class is vrij te kiezen.
 * Deze class moet niet public zijn, ze mag ook (zoals hier) de package visibility hebben.
 * Zoals je variabelen niet meer visibility geeft dan nodig, 
 * geef je ook classes niet meer visibility dan nodig.
 */
class IndexController {
	// *** Multithreading *** vb: maak een teller die het aantal requests over de browser heen bijhoudt.
	// De constructor van AtomicInteger initialiseert de getalwaarde in die AtomicInteger op 0.
	private final AtomicInteger aantalKeerBekeken = new AtomicInteger();
	/*
	 * Om ook op de welkomspagina de identificatie van de gebruiker te tonen, 
	 * voeg je een private variabele toe die verwijst naar de data in de session van de gebruiker.
	 */
	private final Identificatie identificatie;
	// Je voegt een constructor toe met dependency injection.
	IndexController(Identificatie identificatie) {
		this.identificatie = identificatie;
	}
	
	// Je tikt @GetMapping voor een method die browser GET requests verwerkt.
	@GetMapping
	/*
	 * De naam van de method is vrij te kiezen.
	 * De method moet een String teruggeven.
	 * De method moet niet public zijn, ze mag ook package visibility hebben.
	 */
//	String index() {
////		int uur = LocalTime.now().getHour();
////		if (uur < 12) {
////			// De returnwaarde van de method is de response die Spring naar de browser terugstuurt.
////			return "Goede morgen";
////		}
////		if (uur < 18) {
////			return "Goede middag";
////		}
////		return "Goede avond";
//		
//		// *** Controller en JSP combineren ***
//		/*
//		 * De returnwaarde van de method, die de browser request verwerkt,
//		 * is de plaats en de naam van de JSP die de response naar de browser zal sturen.
//		 */
//		return "/WEB-INF/JSP/index.jsp";
//	}
	/*
	 *  *** Data doorgeven van de controller naar de JSP ***
	 *  Een controller die data doorgeeft aan de JSP moet als returntype ModelAndView hebben.
	 */
//	ModelAndView index() {
	/*
	 * *** Wijzig de method-declaratie om met Cookies te werken ***
	 * Spring zal de inhoud van de cookie met de naam laatstBezocht automatisch invullen in 
	 * de method parameter die volgt op @CookieValue: laatstBezocht.
	 * Standaard krijg je een fout als deze cookie niet bestaat.
	 * Door required op false te plaatsen los je dit op: 
	 * als de cookie niet bestaat vult Spring de method parameter laatstBezocht met null.
	 */
	ModelAndView index(@CookieValue(name="laatstBezocht", required = false) 
				/*
				 * Om een cookie te schrijven heb je een object nodig van het type HttpServletResponse.
				 * Dit low-level object laat toe de response verfijnd in te stellen.
				 */
				String laatstBezocht, HttpServletResponse response) {
		String boodschap;
		int uur = LocalTime.now().getHour();
		if (uur < 12) {
			boodschap = "Goede morgen";
		} else if (uur < 18) {
			boodschap = "Goede middag";
		} else {
			boodschap = "Goede avond";
		}
		/*
		 * De 1° parameter van de ModelAndView constructor is de naam van de JSP 
		 * waaraan de controller de request doorgeeft.
		 * De 2° parameter is de naam waaronder de controller data doorgeeft.
		 * De 3° parameter is de data zelf.
		 */
//		return new ModelAndView("/WEB-INF/JSP/index.jsp", "boodschap", boodschap);
		/*
		 *  nadat in application.properties de plaats en de extensie van de JSP's 
		 *  zijn opgegeven als prefix en suffix:
		 */
//		return new ModelAndView("index", "boodschap", boodschap);
		// *** met JavaBean ***
//		return new ModelAndView("index", "boodschap", boodschap)
//				.addObject("zaakvoerder", new Persoon("Luigi", "Peperone", 7, true,
//						new Adres("Grote markt", "3", 9700, "Oudenaarde")));
		
		// *** Wijzig het return statement van de method die met cookies werkt ***
		// Maakt een cookie met de naam laatstBezocht en als inhoud de systeemtijd.
		Cookie cookie = new Cookie("laatstBezocht", LocalDateTime.now().toString());
		// Laat de cookie na 365 dagen vervallen
		cookie.setMaxAge(31_536_000);
		// Voegt deze cookie toe aan de response die naar de browser gestuurd wordt.
		response.addCookie(cookie);
		ModelAndView modelAndView = new ModelAndView("index", "boodschap", boodschap)
				.addObject("zaakvoerder", new Persoon("Luigi", "Peperone", 7, true,
						new Adres("Grote markt", "3", 9700, "Oudenaarde")));
		/*
		 * Als je de cookie laatstBezocht (ingesteld bij een vorig bezoek aan deze pagina)
		 * kon lezen, geef je hem door aan de JSP als data met de naam laatstBezocht
		 */
		if (laatstBezocht != null) {
//			modelAndView.addObject("laatstBezocht", laatstBezocht);
			/*
			 * De method parse verwacht een String met een datum-tijd als parameter.
			 * De method geeft een LocalDateTime terug met dezelfde datum-tijd waarde.
			 */
			modelAndView.addObject("laatstBezocht", new DatumTijd(LocalDateTime.parse(laatstBezocht)));		// Na toevoegen van een class DatumTijd
		}
		/*
		 * De method incrementAndGet verhoogt de teller in de AtomicInteger op een thread-safe manier
		 * en geeft deze teller terug als returnwaarde.
		 */
		modelAndView.addObject("aantalKeerBekeken", aantalKeerBekeken.incrementAndGet());
		modelAndView.addObject("identificatie", identificatie);
		return modelAndView;
	}
}

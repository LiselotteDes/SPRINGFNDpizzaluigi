package be.vdab.pizzaluigi.web;
import java.time.LocalTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// Je tikt @RestController voor een class die dient als controller
@RestController
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
	// Je tikt @GetMapping voor een method die browser GET requests verwerkt.
	@GetMapping
	/*
	 * De naam van de method is vrij te kiezen.
	 * De method moet een String teruggeven.
	 * De method moet niet public zijn, ze mag ook package visibility hebben.
	 */
	String index() {
		int uur = LocalTime.now().getHour();
		if (uur < 12) {
			// De returnwaarde van de method is de response die Spring naar de browser terugstuurt.
			return "Goede morgen";
		}
		if (uur < 18) {
			return "Goede middag";
		}
		return "Goede avond";
	}
}

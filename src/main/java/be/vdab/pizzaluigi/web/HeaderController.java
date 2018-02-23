package be.vdab.pizzaluigi.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/*
 * Hoe je in een controller method de inhoud van een request header leest.
 * Vb: leest de inhoud van de request header user-agent.
 */

@Controller
@RequestMapping("headers")
class HeaderController {
	private final static String VIEW = "headers";
	@GetMapping
	/*
	 * Spring zal de inhoud van de request header user-agent automatisch invullen 
	 * in de method parameter die volgt op @RequestHeader: userAgent.
	 */
	ModelAndView opWindows(@RequestHeader("user-agent") String userAgent) {
		return new ModelAndView(VIEW, 
				/*
				 * Je geeft aan de JSP data met de naam opWindows door.
				 * Dit zal true bevatten als de request header user-agent het woord windows bevat.
				 */
				"opWindows", userAgent.toLowerCase().contains("windows"));
	}
}

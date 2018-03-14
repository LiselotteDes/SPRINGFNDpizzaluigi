package be.vdab.pizzaluigi.web;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.pizzaluigi.entities.Pizza;
import be.vdab.pizzaluigi.services.PizzaService;
@Controller
@RequestMapping("mandje")
class MandjeController {
	private final Mandje mandje;
	private final PizzaService pizzaService;
	MandjeController(Mandje mandje, PizzaService pizzaService) {
		this.mandje = mandje;
		this.pizzaService = pizzaService;
	}
	/*
	 * Deze method krijgt een verzameling pizza id's als parameter binnen. Dit zullen de pizza id's zijn die je onthoudt in het mandje 
	 * in de session van de gebruiker.
	 * De method geeft de verzameling Pizza objecten terug die bij het mandje horen.
	 * Je zal deze verzameling doorgeven aan de JSP, die hiermee de pizza's in het mandje zal afbeelden.
	 */
	private List<Pizza> maakPizzasVanPizzaIds(List<Long> pizzaIds) {
		List<Pizza> pizzas = new ArrayList<>(pizzaIds.size());
		for (long id: pizzaIds) {
			pizzaService.read(id).ifPresent(pizza -> pizzas.add(pizza));
		}
		return pizzas;
	}
	private static final String VIEW = "mandje";
	@GetMapping
	ModelAndView toonMandje() {
		return new ModelAndView(VIEW)
				// Je maakt een form object en geeft het door aan de JSP.
				.addObject(new MandjeForm())
				// Om een pizza toe te voegen zal de gebruiker een pizza kiezen uit de lijst met alle pizza's. Je geeft deze lijst door aan de JSP.
				.addObject("allePizzas", pizzaService.findAll())
				// De JSP toont ook de pizza's die momenteel in het mandje liggen. Je geeft deze pizza's door aan de JSP.
				.addObject("pizzasInMandje", maakPizzasVanPizzaIds(mandje.getPizzaIds()));
	}
	private static final String REDIRECT_NA_TOEVOEGEN = "redirect:/mandje";
	@PostMapping
	String voegPizzaToeAanMandje(MandjeForm form) {
		mandje.addPizzaId(form.getPizzaId());
		return REDIRECT_NA_TOEVOEGEN;
	}
}

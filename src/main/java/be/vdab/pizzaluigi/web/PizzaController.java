package be.vdab.pizzaluigi.web;
import java.math.BigDecimal;
//import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.pizzaluigi.entities.Pizza;
import be.vdab.pizzaluigi.services.EuroService;
/*
 * Een PizzaController die GET requests verwerkt naar de URL pizzas.html:
 */
@Controller
@RequestMapping("pizzas")
class PizzaController {
	private final static String PIZZAS_VIEW = "pizzas";
	private final static String PIZZA_VIEW = "pizza";
	private final static String PRIJZEN_VIEW = "prijzen";
//	private final List<Pizza> pizzas = Arrays.asList(
//			new Pizza(12, "Prosciutto", BigDecimal.valueOf(4), true),
//			new Pizza(14, "Margherita", BigDecimal.valueOf(5), false),
//			new Pizza(17, "Calzone", BigDecimal.valueOf(4), false));
	private final Map<Long, Pizza> pizzas = new LinkedHashMap<>();	// keys:pizza ids
	private final EuroService euroService;
	/*
	 * Bij het unit testen van de controller roep je de constructor op en geef je
	 * een dummyEuroService object mee.
	 * Bij het uitvoeren van de website roept Spring deze constructor op en geeft de bean mee die
	 * deze interface implementeert: de bean gebaseerd op de class DefaultEuroService.
	 */
	PizzaController(EuroService euroService) {
		pizzas.put(12L, new Pizza(12, "Prosciutto", BigDecimal.valueOf(4), true));
		pizzas.put(14L, new Pizza(14, "Margherita", BigDecimal.valueOf(5), false));
		pizzas.put(17L, new Pizza(17, "Calzone", BigDecimal.valueOf(4), false));
		pizzas.put(23L, new Pizza(23, "Fungi & Olive", BigDecimal.valueOf(5), false));
		this.euroService = euroService;
	}
	@GetMapping
	ModelAndView pizzas() {
		return new ModelAndView(PIZZAS_VIEW, "pizzas", pizzas);
	}
	/*
	 * Je geeft aan dat GET requests naar pizzas door de method pizza verwerkt worden,
	 * maar enkel als de query string een parameter id bevat.
	 */
//	@GetMapping(params="id")
	/*
	 * Bij de class staat @RequestMapping("pizzas").
	 * De class verwerkt dus requests naar URL's die beginnen met /pizzas.
	 * Je voegt hier {id} aan toe.
	 * Je bekomt zo de URI template pizzas/{id}.
	 * De method pizza verwerkt zo requests naar URL's die passen bij /pizzas/{id}.
	 */
	@GetMapping("{id}")
	/*
	 * Je wil de inhoud van die parameter id kennen.
	 * Het volstaat daartoe een parameter met dezelfde naam aan je Java method toe te voegen.
	 * Spring zal de inhoud van de parameter id in de query string 
	 * overbrengen naar deze method parameter.
	 */
//	ModelAndView pizza(long id) {
	/*
	 * Je tikt voor een method parameter @PathVariable.
	 * Spring vult die method parameter met 
	 * de waarde van de path variabele met dezelfde naam (id) in de URL van de binnengekomen request.
	 */
	ModelAndView pizza(@PathVariable long id) {
		/*
		 * Gebruikt voor het eerst de ModelAndView constructor met één parameter:
		 * de naam van de JSP.
		 * Verder in de code geef je met de addObject method ook data door aan die JSP.
		 */
		ModelAndView modelAndView = new ModelAndView(PIZZA_VIEW);
		Pizza pizza = pizzas.get(id);
		modelAndView.addObject(pizza);
		modelAndView.addObject("inDollar", euroService.naarDollar(pizza.getPrijs()));
		return modelAndView;
	}
	@GetMapping("prijzen")
	ModelAndView prijzen() {
		return new ModelAndView(PRIJZEN_VIEW, "prijzen", 
				pizzas.values().stream()
				.map(pizza -> pizza.getPrijs()).distinct().collect(Collectors.toSet()));
	}
	@GetMapping(params="prijs")
	ModelAndView pizzasVanPrijs(BigDecimal prijs) {
		return new ModelAndView(PRIJZEN_VIEW, "pizzas",
				pizzas.values().stream()
					.filter(pizza -> pizza.getPrijs().equals(prijs))
					.collect(Collectors.toList()))
				.addObject("prijs", prijs)
				.addObject("prijzen", pizzas.values().stream()
						.map(pizza -> pizza.getPrijs()).distinct().collect(Collectors.toSet()));
	}
}

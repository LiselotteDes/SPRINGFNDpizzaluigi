package be.vdab.pizzaluigi.web;
import java.math.BigDecimal;
//import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.pizzaluigi.entities.Pizza;
/*
 * Een PizzaController die GET requests verwerkt naar de URL pizzas.html:
 */
@Controller
@RequestMapping("pizzas")
class PizzaController {
	private final static String PIZZAS_VIEW = "pizzas";
//	private final List<Pizza> pizzas = Arrays.asList(
//			new Pizza(12, "Prosciutto", BigDecimal.valueOf(4), true),
//			new Pizza(14, "Margherita", BigDecimal.valueOf(5), false),
//			new Pizza(17, "Calzone", BigDecimal.valueOf(4), false));
	private final Map<Long, Pizza> pizzas = new LinkedHashMap<>();	// keys:pizza ids
	PizzaController() {
		pizzas.put(12L, new Pizza(12, "Prosciutto", BigDecimal.valueOf(4), true));
		pizzas.put(14L, new Pizza(14, "Margherita", BigDecimal.valueOf(5), false));
		pizzas.put(17L, new Pizza(17, "Calzone", BigDecimal.valueOf(4), false));
		pizzas.put(23L, new Pizza(23, "Fungi & Olive", BigDecimal.valueOf(5), false));
	}
	@GetMapping
	ModelAndView pizzas() {
		return new ModelAndView(PIZZAS_VIEW, "pizzas", pizzas);
	}
}

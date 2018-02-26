package be.vdab.pizzaluigi.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

public class PizzaControllerTest {
	private PizzaController controller;
	@Before
	public void before() {
		controller = new PizzaController();
	}
	@Test
	public void pizzasWerktSamenMetDeJuisteJSP() {
		ModelAndView modelAndView = controller.pizzas();
		assertEquals("pizzas", modelAndView.getViewName());
	}
	@Test
	public void pizzasGeeftPizzasAanJsp() {
		ModelAndView modelAndView = controller.pizzas();
		assertTrue(modelAndView.getModel().containsKey("pizzas"));
	}
	@Test
	public void pizzaWerktSamenMetDeJuisteJSP() {
		ModelAndView modelAndView = controller.pizza(1);
		assertEquals("pizza", modelAndView.getViewName());
	}
	@Test
	public void pizzaGeeftPizzaAanJSP() {
		ModelAndView modelAndView = controller.pizza(1);
		assertTrue(modelAndView.getModel().containsKey("pizza"));
	}
}

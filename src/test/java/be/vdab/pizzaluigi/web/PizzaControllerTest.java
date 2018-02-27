package be.vdab.pizzaluigi.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.pizzaluigi.services.EuroService;

public class PizzaControllerTest {
	private PizzaController pizzaController;
	private EuroService dummyEuroService;
	@Before
	public void before() {
		dummyEuroService = Mockito.mock(EuroService.class);
		pizzaController = new PizzaController(dummyEuroService);
	}
	@Test
	public void pizzasWerktSamenMetDeJuisteJSP() {
		ModelAndView modelAndView = pizzaController.pizzas();
		assertEquals("pizzas", modelAndView.getViewName());
	}
	@Test
	public void pizzasGeeftPizzasAanJsp() {
		ModelAndView modelAndView = pizzaController.pizzas();
		assertTrue(modelAndView.getModel().containsKey("pizzas"));
	}
	@Test
	public void pizzaWerktSamenMetDeJuisteJSP() {
		ModelAndView modelAndView = pizzaController.pizza(12);
		assertEquals("pizza", modelAndView.getViewName());
	}
	@Test
	public void pizzaGeeftPizzaAanJSP() {
		ModelAndView modelAndView = pizzaController.pizza(12);
		assertTrue(modelAndView.getModel().containsKey("pizza"));
	}
}

package be.vdab.pizzaluigi.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;
import be.vdab.pizzaluigi.entities.Pizza;
import be.vdab.pizzaluigi.services.EuroService;
import be.vdab.pizzaluigi.services.PizzaService;

public class PizzaControllerTest {
	private PizzaController pizzaController;
	private EuroService dummyEuroService;
	private PizzaService dummyPizzaService;
	@Before
	public void before() {
		dummyEuroService = Mockito.mock(EuroService.class);
		dummyPizzaService = Mockito.mock(PizzaService.class);
		Mockito.when(dummyPizzaService.read(1)).thenReturn(Optional.of(new Pizza(1, "Test", BigDecimal.ONE, true)));
		pizzaController = new PizzaController(dummyEuroService, dummyPizzaService);
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
		ModelAndView modelAndView = pizzaController.pizza(1);
		assertEquals("pizza", modelAndView.getViewName());
	}
	@Test
	public void pizzaGeeftPizzaAanJSP() {
		ModelAndView modelAndView = pizzaController.pizza(1);
		assertTrue(modelAndView.getModel().containsKey("pizza"));
	}
}

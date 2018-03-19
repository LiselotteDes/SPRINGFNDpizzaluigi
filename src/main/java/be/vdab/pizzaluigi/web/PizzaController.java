package be.vdab.pizzaluigi.web;
import java.math.BigDecimal;
//import java.util.Arrays;
//import java.util.LinkedHashMap;
import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
// Om request parameters te kunnen toevoegen aan de URL waarnaar je redirect.
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import be.vdab.pizzaluigi.entities.Pizza;
import be.vdab.pizzaluigi.services.EuroService;
import be.vdab.pizzaluigi.services.PizzaService;
import be.vdab.pizzaluigi.valueobjects.Dollar;
// Een PizzaController die GET requests verwerkt naar de URL pizzas.html:
@Controller
@RequestMapping("pizzas")
class PizzaController {
	private final static String PIZZAS_VIEW = "pizzas";
	private final static String PIZZA_VIEW = "pizza";
	private final static String PRIJZEN_VIEW = "prijzen";
	private final static String VAN_TOT_PRIJS_VIEW = "vantotprijs";
	private final static String TOEVOEGEN_VIEW = "toevoegen";
	// Als je een URL zal gebruiken als redirect URL, tik je voor de URL redirect:
	private final static String REDIRECT_URL_NA_TOEVOEGEN = "redirect:/pizzas";
//	private final List<Pizza> pizzas = Arrays.asList(
//			new Pizza(12, "Prosciutto", BigDecimal.valueOf(4), true),
//			new Pizza(14, "Margherita", BigDecimal.valueOf(5), false),
//			new Pizza(17, "Calzone", BigDecimal.valueOf(4), false));
//	private final Map<Long, Pizza> pizzas = new LinkedHashMap<>();	// keys:pizza ids
	private final EuroService euroService;
	private final PizzaService pizzaService;
	/*
	 * Bij het unit testen van de controller roep je de constructor op en geef je
	 * een dummyEuroService object mee.
	 * Bij het uitvoeren van de website roept Spring deze constructor op en geeft de bean mee die
	 * deze interface implementeert: de bean gebaseerd op de class DefaultEuroService.
	 */
	PizzaController(EuroService euroService, PizzaService pizzaService) {
//		pizzas.put(12L, new Pizza(12, "Prosciutto", BigDecimal.valueOf(4), true));
//		pizzas.put(14L, new Pizza(14, "Margherita", BigDecimal.valueOf(5), false));
//		pizzas.put(17L, new Pizza(17, "Calzone", BigDecimal.valueOf(4), false));
//		pizzas.put(23L, new Pizza(23, "Fungi & Olive", BigDecimal.valueOf(5), false));
		this.euroService = euroService;
		this.pizzaService = pizzaService;
	}
	@GetMapping
	ModelAndView pizzas() {
//		return new ModelAndView(PIZZAS_VIEW, "pizzas", pizzas);
		return new ModelAndView(PIZZAS_VIEW, "pizzas", pizzaService.findAll());
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
//		Pizza pizza = pizzas.get(id);
//		modelAndView.addObject(pizza);
//		modelAndView.addObject("inDollar", euroService.naarDollar(pizza.getPrijs()));
		pizzaService.read(id).ifPresent(pizza -> {
			modelAndView.addObject(pizza);
//			modelAndView.addObject("inDollar", euroService.naarDollar(pizza.getPrijs()));
			modelAndView.addObject("inDollar", new Dollar(euroService.naarDollar(pizza.getPrijs())));
		});
		return modelAndView;
	}
	@GetMapping("prijzen")
	ModelAndView prijzen() {
		return new ModelAndView(PRIJZEN_VIEW, "prijzen", 
//				pizzas.values().stream()
//				.map(pizza -> pizza.getPrijs()).distinct().collect(Collectors.toSet()));
				pizzaService.findUniekePrijzen());
	}
	@GetMapping(params="prijs")
	ModelAndView pizzasVanPrijs(BigDecimal prijs) {
		return new ModelAndView(PRIJZEN_VIEW, "pizzas",
//				pizzas.values().stream()
//					.filter(pizza -> pizza.getPrijs().equals(prijs))
//					.collect(Collectors.toList()))
				pizzaService.findByPrijs(prijs))
				.addObject("prijs", prijs)
//				.addObject("prijzen", pizzas.values().stream()
//						.map(pizza -> pizza.getPrijs()).distinct().collect(Collectors.toSet()));
				.addObject("prijzen", pizzaService.findUniekePrijzen());
	}
	/*
	 * De HTML form tonen aan de gebruiker:
	 * Je toont de form bij een GET request naar de url /pizzas/vantotprijs.
	 * Je verwerkt die request in deze method.
	 */
	@GetMapping("vantotprijs")
	ModelAndView findVanTotPrijs() {
		VanTotPrijsForm form = new VanTotPrijsForm();
		/*
		 * Initialiseert de properties van het form object.
		 * Het resultaat hiervan is dat als de gebruiker in de pagina binnenkomt, de 2 invoervakken de waarde nul bevatten.
		 * Als je liever hebt dat deze invoervakken leeg zijn, verwijder je deze regels.
		 */
		form.setVan(BigDecimal.ZERO);
		form.setTot(BigDecimal.ZERO);
		// Je geeft het form object aan de JSP onder de naam vanTotPrijsForm ("parameter name generation")
		return new ModelAndView(VAN_TOT_PRIJS_VIEW).addObject(form);
	}
	/*
	 * De HTML form verwerken na de submit:
	 * Als de gebruiker de form submit, verstuurt de browser een request naar de action url, met de inhoud van de tekstvakken in de query string,
	 * bv: /pizzas/van=3&tot=4.
	 * Spring verwerkt deze request door met de default constructor een form object te maken 
	 * en vervolgens met de setters de inhoud van de vakken mee te geven.
	 * 
	 * Deze method verwerkt GET requests naar /pizzas,
	 * op voorwaarde dat die de request parameters van en tot bevat:
	 */
	@GetMapping(params = {"van", "tot"})
	/*
	 * De method heeft een VanTotPrijsForm parameter.
	 * Spring ziet dit en maakt een VanTotPrijsForm object met de default constructor.
	 * Spring ziet dat de request een parameter van bevat.
	 * Spring ziet dat de class VanTotPrijsForm een method setVan bevat.
	 * Spring roept setVan op en geeft de waarde van de request parameter van mee.
	 * Spring ziet dat de request ook een parameter tot bevat.
	 * Spring ziet dat de class VanTotPrijsForm een method setTot bevat.
	 * Spring roept setTot op en geeft de waarde van de request parameter tot mee.
	 * Daarna roept Spring de huidige method findVanTotPrijs op en geeft zijn opgevuld VanTotPrijsForm object mee.
	 */
	ModelAndView findVanTotPrijs(@Valid VanTotPrijsForm form,
			/*
			 * Je kan het mislukken van de conversie (inhoud vh invoervak naar het setter parameter type) nazien i/e object v/h type BindingResult.
			 * Deze BindingResult parameter moet volgen op de parameter met het form object.
			 */
			BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView(VAN_TOT_PRIJS_VIEW);
		// De method hasErrors geeft true als de DataBinder fouten ontdekte bij de form validatie.
		if (bindingResult.hasErrors()) {
			return modelAndView;
		}
		// Je zoekt enkel pizza's als er geen fouten waren.
		// Je zoekt de pizza's met de PizzaService method findByPrijsBetween.
		List<Pizza> pizzas = pizzaService.findByPrijsBetween(form.getVan(), form.getTot());
		if (pizzas.isEmpty()) {
			/*
			 * Maakt een ALGEMENE boodschap die niet aan één vak verbonden is.
			 * De key van de foutboodschap is geenPizzas.
			 */
			bindingResult.reject("geenPizzas");
		} else {
			// Je geeft deze pizza's onder de naam pizzas door aan de JSP.
			modelAndView.addObject("pizzas", pizzas);
		}
		return modelAndView;
	}
	/*
	 * Als de gebruiker in het menu Toevoegen kiest, doet hij een GET request naar /pizzas/toevoegen.
	 * Je toont dan de toevoegpagina.
	 */
	@GetMapping("toevoegen")
	ModelAndView toevoegen() {
		// Geeft een Pizza object door aan de JSP. Je zal dit object daar gebruiken als form object.
		return new ModelAndView(TOEVOEGEN_VIEW).addObject(new Pizza());
	}
	/*
	 * Wanneer de gebruiker de form (hierboven) submit, stuurt de browser een POST request naar de webserver. Volgende method verwerkt die request.
	 * 
	 * Je geeft aan dat de method die op deze regel volgt POST requests verwerkt.
	 * De method verwerkt POST requests naar de URL vermeld bij @RequestMappings (pizzas), 
	 * gecombineerd met de URL vermeld bij @PostMapping (toevoegen): pizzas/toevoegen.
	 */
	@PostMapping("toevoegen")
	ModelAndView toevoegen(@Valid Pizza pizza, BindingResult bindingResult,
			// Je kan met een RedirectAttributes parameter verder in de code request parameters toevoegen aan de URL waarnaar je redirect.
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			// Als er validatiefouten zijn toon je de pagina met de form opnieuw.
			return new ModelAndView(TOEVOEGEN_VIEW);
		}
		// Voegt de pizza toe aan de database.
		pizzaService.create(pizza);
		// Toont de pagina met alle pizzas.
//		return new ModelAndView(PIZZAS_VIEW, "pizzas", pizzaService.findAll());
		/*
		 * Spring zal aan de redirect URL een request parameter boodschap toevoegen met de inhoud 'Pizza toegevoegd'.
		 * Spring zal daarbij de spatie vervangen door het plus teken.
		 */
		redirectAttributes.addAttribute("boodschap", "Pizza toegevoegd");
		/*
		 * Wanneer je aan de ModelAndView constructor een String meegeeft die begint met redirect:/, 
		 * gebruikt Spring deze String niet om een JSP te zoeken die HTML afbeeldt,
		 * maar gebruikt Spring deze String om een redirect response naar de browser te sturen.
		 */
		return new ModelAndView(REDIRECT_URL_NA_TOEVOEGEN);
	}
}

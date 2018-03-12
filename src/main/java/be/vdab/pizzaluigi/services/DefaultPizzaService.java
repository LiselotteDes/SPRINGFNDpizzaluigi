package be.vdab.pizzaluigi.services;
import be.vdab.pizzaluigi.entities.Pizza;
import be.vdab.pizzaluigi.repositories.PizzaRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
// Met @Service voor een class, maakt Spring bij het starten van de website een bean van deze class en houdt die bij zolang de website draait.
@Service
/*
 * Met @Transactional voor een class, is elke method in die class 1 transactie: Spring verzamelt alle db-bewerkingen van een method in 1 transactie.
 * Stelt de transactie-eigenschappen readOnly, isolation level en timeout in.
 */
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
class DefaultPizzaService implements PizzaService {
	private final PizzaRepository pizzaRepository;
	// Injecteert de bean die de interface PizzaRepository implementeert: de bean gebaseerd op de class JdbcPizzaRepository.
	DefaultPizzaService(PizzaRepository pizzaRepository) {
		this.pizzaRepository = pizzaRepository;
	}
	@Override
	/*
	 * Overschrijft de transactie eigenschappen beschreven voor de class.
	 * Opgelet: Geef niet enkel de parameter mee die wijzigt tov de class (readOnly), maar ook de parameter die gelijk blijft (isolation) moet erbij.
	 */
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void create(Pizza pizza) {
		pizzaRepository.create(pizza);
	}
	@Override
	public Optional<Pizza> read(long id) {
		return pizzaRepository.read(id);
	}
	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void update(Pizza pizza) {
		pizzaRepository.update(pizza);
	}
	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void delete(long id) {
		pizzaRepository.delete(id);
	}
	@Override
	public List<Pizza> findAll() {
		return pizzaRepository.findAll();
	}
	@Override
	public List<Pizza> findByPrijsBetween(BigDecimal van, BigDecimal tot) {
		return pizzaRepository.findByPrijsBetween(van, tot);
	}
	@Override
	public long findAantalPizzas() {
		return pizzaRepository.findAantalPizzas();
	}
	@Override
	public List<BigDecimal> findUniekePrijzen() {
		return pizzaRepository.findUniekePrijzen();
	}
	@Override
	public List<Pizza> findByPrijs(BigDecimal prijs) {
		return pizzaRepository.findByPrijs(prijs);
	}
}

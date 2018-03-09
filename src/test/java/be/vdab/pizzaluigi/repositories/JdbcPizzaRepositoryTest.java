package be.vdab.pizzaluigi.repositories;
import static org.junit.Assert.*;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import be.vdab.pizzaluigi.entities.Pizza;
import junit.framework.Assert;
/*
 * Integration test van een repository bean: test de repository bean met zijn samenwerking met de database.
 * De test is zo geschreven dat het niet uitmaakt of er reeds records in de db aanwezig zijn en hoeveel records er reeds in de db aanwezig zijn.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
// Zorgt ervoor dat iedere test binnen zijn eigen transactie wordt uitgevoerd die op het einde van de test gerollbacked wordt.
@Transactional
public class JdbcPizzaRepositoryTest {
	@Autowired
	private JdbcPizzaRepository repository;
	// Injecteert de NamedParameterJdbcTemplate in de unit test om daarmee op korte manier SQL statements uit te voeren in de testen.
	@Autowired
	private NamedParameterJdbcTemplate template;
	@Test
	public void create() {
		Pizza pizza = new Pizza();
		pizza.setNaam("integration test");
		pizza.setPrijs(BigDecimal.TEN);
		repository.create(pizza);
		assertEquals(Integer.valueOf(1), template.queryForObject(
				"select count(*) from pizzas where naam='integration test'", Collections.emptyMap(), Integer.class));
	}
	// Je roept deze method in meerdere tests op om minstens één record in de database te hebben.
	private long voegPizzaToe() {
		template.update("insert into pizzas(naam,prijs,pikant) values('integration test', 10, false)", Collections.emptyMap());
		return template.queryForObject("select max(id) from pizzas", Collections.emptyMap(), Long.class);
	}
	@Test
	public void read() {
		long hoogsteId = voegPizzaToe();
		assertEquals("integration test", repository.read(hoogsteId).get().getNaam());
	}
	@Test
	public void update() {
		long hoogsteId = voegPizzaToe();
		Pizza pizza = new Pizza(hoogsteId, "integration test", BigDecimal.ONE, false);
		repository.update(pizza);
		assertEquals(0, BigDecimal.ONE.compareTo(
				template.queryForObject("select prijs from pizzas where id=:id", Collections.singletonMap("id", hoogsteId), BigDecimal.class)));
	}
	// Je roept deze method in meerdere tests op.
	private long aantalPizzas() {
		return template.queryForObject("select count(*) from pizzas", Collections.emptyMap(), Long.class);
	}
	@Test
	public void  delete() {
		long hoogsteId = voegPizzaToe();
		long aantalPizzas = aantalPizzas();
		repository.delete(hoogsteId);
		assertEquals(Long.valueOf(aantalPizzas - 1),
				template.queryForObject("select count(*) from pizzas", Collections.emptyMap(), Long.class));
	}
	@Test
	public void findAllGeeftAllePizzas() {
		voegPizzaToe();
		long aantalPizzas = aantalPizzas();
		assertEquals(aantalPizzas, repository.findAll().size());
	}
	@Test
	public void findAllGeeftPizzasOplopendOpId() {
		voegPizzaToe();
		long vorigId = 0;
		for (Pizza pizza: repository.findAll()) {
			assertTrue(pizza.getId() > vorigId);
			vorigId = pizza.getId();
		}
	}
	@Test
	public void findByPrijsBetweenGeeftJuistePizzas() {
		voegPizzaToe();
		List<Pizza> pizzas = repository.findByPrijsBetween(BigDecimal.ONE, BigDecimal.TEN);
		pizzas.stream().forEach(pizza -> {
			assertTrue(pizza.getPrijs().compareTo(BigDecimal.ONE) >= 0);
			assertTrue(pizza.getPrijs().compareTo(BigDecimal.TEN) <= 0);
		});
	}
	@Test
	public void findByPrijsBetweenGeeftJuistAantalPizzas() {
		voegPizzaToe();
		long aantalPizzas = template.queryForObject("select count(*) from pizzas where prijs between 1 and 10", Collections.emptyMap(), Long.class);
		assertEquals(aantalPizzas, repository.findByPrijsBetween(BigDecimal.ONE, BigDecimal.TEN).size());
	}
	@Test
	public void findAantal() {
		voegPizzaToe();
		RowMapper<Long> idRowMapper = (resultSet, rowNum) -> resultSet.getLong("id");
		long aantalPizzas = template.query("select id from pizzas", idRowMapper).size();
		assertEquals(aantalPizzas, repository.findAantalPizzas());
	}
	@Test
	public void findUniekePrijzenGeeftOplopendePrijzen() {
		voegPizzaToe();
		BigDecimal vorigePrijs = BigDecimal.valueOf(-1);
//		for(BigDecimal prijs : repository.findUniekePrijzen()) {
//			assertTrue(prijs.compareTo(vorigePrijs) > 0);
//		}
		
		repository.findUniekePrijzen().stream().forEach(prijs -> assertTrue(prijs.compareTo(vorigePrijs) > 0));
	}
	@Test
	public void findByPrijsGeeftJuistePizzas() {
		voegPizzaToe();
		repository.findByPrijs(BigDecimal.TEN).stream().forEach(pizza -> assertEquals(0, BigDecimal.TEN.compareTo(pizza.getPrijs())));
	}
	@Test
	public void findByPrijsGeeftJuistAantalPizzas() {
		voegPizzaToe();
		long aantalPizzas = template.queryForObject("select count(*) from pizzas where prijs=10", Collections.emptyMap(), Long.class);
		assertEquals(aantalPizzas, repository.findByPrijs(BigDecimal.TEN).size());
	}
}

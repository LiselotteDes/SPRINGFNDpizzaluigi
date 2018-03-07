package be.vdab.pizzaluigi.repositories;
import static org.junit.Assert.*;
import java.math.BigDecimal;
import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import be.vdab.pizzaluigi.entities.Pizza;
/*
 * Integration test van een repository bean: test de repository bean met zijn samenwerking met de database.
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
	/*
	 * 
	 */
	private long voegPizzaToe() {
		
	}
}

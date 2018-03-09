package be.vdab.pizzaluigi.repositories;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import be.vdab.pizzaluigi.entities.Pizza;

/*
 * Je tikt @Repository voor de repository class.
 * Spring maakt dan bij het starten van de website een bean van deze class en houdt die bij zolang de website draait.
 */
@Repository
class JdbcPizzaRepository implements PizzaRepository {
	private final NamedParameterJdbcTemplate template;
	// Het resultaat van een select statement met één rij met één kolom is een scalar value.
	private static final String SQL_FIND_AANTAL_PIZZAS = "select count(*) from pizzas";
	/*
	 * Een update of delete SQL statement met één parameter:
	 * Je geeft de parameter aan met een : teken gevolgd door de naam van de parameter.
	 */
	private static final String SQL_DELETE_PIZZA = 
			"delete from pizzas where id = :id";
	// Een update of delete SQL statment met meerdere parameters:
	private static final String SQL_UPDATE_PIZZA = 
			"update pizzas set naam = :naam, prijs = :prijs, pikant = :pikant " + 
			"where id = :id";
	// Een record voeg je toe met de class SimpleJdbcInsert:
	private final SimpleJdbcInsert insert;
	/*
	 * Implementeert de functionele interface RowMapper met een labmda.
	 * Je gebruikt deze variabele als parameter van de leesoperaties van NamedParameterJdbcTemplate.
	 * Die zetten gelezen records om naar entities met deze RowMapper en bieden je die entities aan.
	 */
	private final RowMapper<Pizza> pizzaRowMapper = (resultSet, rowNum) -> 
		new Pizza(resultSet.getLong("id"), resultSet.getString("naam"), resultSet.getBigDecimal("prijs"), resultSet.getBoolean("pikant"));
	private static final String SQL_SELECT_ALL = 
			"select id, naam, prijs, pikant from pizzas order by id";
	private static final String SQL_SELECT_BY_PRIJS_BETWEEN = 
			"select id, naam, prijs, pikant from pizzas" 
			+ " where prijs between :van and :tot"
			+ " order by prijs";
	private static final String SQL_READ = 
			"select id, naam, prijs, pikant from pizzas where id = :id";
	private final RowMapper<BigDecimal> prijzenRowMapper = (resultSet, rowNum) -> resultSet.getBigDecimal("prijs");
	private static final String SQL_FIND_UNIEKE_PRIJZEN = 
			"select distinct prijs from pizzas order by prijs";
	private static final String SQL_FIND_BY_PRIJS = 
			"select id, naam, prijs, pikant from pizzas where prijs = :prijs";
	// Injecteert de NamedParameterJdbcTemplate bean die Spring aanmaakt.
	JdbcPizzaRepository(NamedParameterJdbcTemplate template,
			// Je injecteert de DataSource bean, omdat je die bij (*) nodig hebt.
			DataSource dataSource) {
		this.template = template;
		/*
		 * Geeft aan de SimpleJdbcInsert constructor een DataSource object mee.
		 * Dit is dezelfde DataSource die NamedParameterJdbcTemplate gebruikt.
		 * De SimpleJdbcInsert zal een connectie uit die DataSource gebruiken tijdens het toevoegen van een record.
		 */
		this.insert = new SimpleJdbcInsert(dataSource);	// (*)
		// Definieert de naam van de table waarin je records wil toevoegen.
		insert.withTableName("pizzas");
		// Als de table een automatisch gegenereerde primary key kolom bevat, vermeld je met de method usingGeneratedKeyColumns de naam van die kolom.
		insert.usingGeneratedKeyColumns("id");
	}
	// *** interface methods implementeren ***
	@Override
	public long findAantalPizzas() {	
		// Een scalar value lees je met de NaedParameterJdbcTemplate method queryForObject:
		return template.queryForObject(
				// De 1° parameter is het select statement
				SQL_FIND_AANTAL_PIZZAS, 
				/*
				 * Je gebruikt de 2° parameter als het SQL statment parameters bevat. Dit is hier niet het geval.
				 * De method emptyMap geeft een lege map terug.
				 */
				Collections.emptyMap(), 
				// De 3° parameter is de gedaante waaronder je de scalar value wenst terug te krijgen als returnwaarde van de method queryForObject.
				Long.class);
	}
	@Override
	public void delete(long id) {
		// Een update of delete SQL statement voer je uit met de NaedParameterJdbcTemplate method update:
		template.update(
				// De 1° parameter is het SQL statement.
				SQL_DELETE_PIZZA, 
				/*
				 * De 2° parameter is een Map.
				 * De keys zijn parameternamen in het SQL statement. 
				 * De values zijn de bijbehorende parameterwaarden.
				 * De method singletonMap maakt een Map met één entry waarvan je de key en de value meegeeft as parameters.
				 */
				Collections.singletonMap("id", id));
	}
	@Override
	public void update(Pizza pizza) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("naam", pizza.getNaam());
		parameters.put("prijs", pizza.getPrijs());
		parameters.put("pikant", pizza.isPikant());
		parameters.put("id", pizza.getId());
		template.update(SQL_UPDATE_PIZZA, parameters);
	}
	@Override
	public void create(Pizza pizza) {
		// Deze Map bevat één entry per in te vullen kolom in het nieuwe record.
		Map<String, Object> kolomWaarden = new HashMap<>();
		// De key is de naam van een in te vullen kolom. De value is de waarde die je in die kolom invult.
		kolomWaarden.put("naam", pizza.getNaam());
		kolomWaarden.put("prijs", pizza.getPrijs());
		kolomWaarden.put("pikant", pizza.isPikant());
		// Een record voeg je toe met een object van de class SimpleJdbcInsert:
		/*
		 * De method executeAndReturnKey voegt een record toe.
		 * De parameter is een Map met kolomnamen en bijbehorende kolomwaarden.
		 * De method maakt zelf een SQL insert statement en voert dit uit.
		 * De method geeft de automatisch gegenereerde primary key waarde als een Number.
		 */
		Number id = insert.executeAndReturnKey(kolomWaarden);
		// Vult de property id van de Pizza entity met de gegenereerde primary key
		pizza.setId(id.longValue());
	}
	@Override
	public List<Pizza> findAll() {
		/*
		 * De method query voert volgende stappen uit:
		 * a) een Conncetion vragen aan de DataSource bean.
		 * b) een Statement met het select statement maken en uitvoeren.
		 * c) een List<Pizza> maken.
		 * d) itereren over de ResultSet van het resultaat van het select statement.
		 * e) per rij de rowMapper method mapRow uitvoeren.
		 * f) de Pizza, die de mapRow method teruggeeft, toevoegen aan de List.
		 * g) de ResultSet, het Statement en de Connection sluiten.
		 * h) de List<Pizza> teruggeven.
		 */
		return template.query(SQL_SELECT_ALL, pizzaRowMapper);
	}
	@Override
	public List<Pizza> findByPrijsBetween(BigDecimal van, BigDecimal tot) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("van", van);
		parameters.put("tot", tot);
		return template.query(SQL_SELECT_BY_PRIJS_BETWEEN, parameters, pizzaRowMapper);
	}
	@Override
	public Optional<Pizza> read(long id) {
		try {
			/*
			 * Je kan met de NamedParameterJdbcTemplate method queryForObject (die je al gebruikte in de method findAll)
			 * één record lezen in de gedaante van een entity.
			 * Als het select statement geen record vindt, of meer dan één record, werpt queryForObject een IncorrectResultSizeDataAccessException.
			 */
			return Optional.of(template.queryForObject(SQL_READ, Collections.singletonMap("id", id), pizzaRowMapper));
		} catch (IncorrectResultSizeDataAccessException ex) {
			return Optional.empty();
		}
	}
	@Override
	public List<BigDecimal> findUniekePrijzen() {
		return template.query(SQL_FIND_UNIEKE_PRIJZEN, prijzenRowMapper);
	}
	@Override
	public List<Pizza> findByPrijs(BigDecimal prijs) {
		return template.query(SQL_FIND_BY_PRIJS, Collections.singletonMap("prijs", prijs), pizzaRowMapper);
	}
}

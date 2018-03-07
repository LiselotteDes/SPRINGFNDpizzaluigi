package be.vdab.pizzaluigi.restclients;

import static org.junit.Assert.*;
import java.math.BigDecimal;
//import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

// Laat JUnit en Spring samenwerken
@RunWith(SpringRunner.class)
// Laat Spring zijn IOC container (met daarin de Spring beans) opbouwen
@SpringBootTest
public class FixerKoersClientTest {
	/*
	 * Als je @Autowired tikt voor een private variabele, zoekt Spring een bean waarvan de class dezelfde is als deze van die variabele.
	 * Als Spring zo'n bean vindt, vult Spring de variabele met deze bean.
	 */
	@Autowired
	private FixerKoersClient client;
//	@Before
//	public void before() {
//		client = new FixerKoersClient();
//	}
	@Test
	public void deKoersMoetPositiefZijn() {
		assertTrue(client.getDollarKoers().compareTo(BigDecimal.ZERO) > 0);
	}

}

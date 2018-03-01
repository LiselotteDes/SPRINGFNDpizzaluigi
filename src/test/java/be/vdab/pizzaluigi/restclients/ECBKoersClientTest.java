package be.vdab.pizzaluigi.restclients;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
// een integration test voor de class ECBKoersClient
public class ECBKoersClientTest {
	private ECBKoersClient client;
	@Before
	public void before() {
		client = new ECBKoersClient();
	}
	@Test
	public void deKoersMoetPositiefZijn() {
		assertTrue(client.getDollarKoers().compareTo(BigDecimal.ZERO) > 0);
	}

}

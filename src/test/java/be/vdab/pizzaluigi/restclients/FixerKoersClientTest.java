package be.vdab.pizzaluigi.restclients;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class FixerKoersClientTest {
	private FixerKoersClient client;
	@Before
	public void before() {
		client = new FixerKoersClient();
	}
	@Test
	public void deKoersMoetPositiefZijn() {
		assertTrue(client.getDollarKoers().compareTo(BigDecimal.ZERO) > 0);
	}

}

package be.vdab.pizzaluigi.restclients;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
// een integration test voor de class ECBKoersClient
@RunWith(SpringRunner.class)
@SpringBootTest
public class ECBKoersClientTest {
	@Autowired
	private ECBKoersClient client;
	@Test
	public void deKoersMoetPositiefZijn() {
		assertTrue(client.getDollarKoers().compareTo(BigDecimal.ZERO) > 0);
	}

}

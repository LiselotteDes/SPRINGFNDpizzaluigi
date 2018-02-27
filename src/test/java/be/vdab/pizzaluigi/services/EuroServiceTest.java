package be.vdab.pizzaluigi.services;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import be.vdab.pizzaluigi.restclients.KoersClient;

public class EuroServiceTest {
	private KoersClient dummyKoersClient;
	private EuroService euroService;
	@Before
	public void before() {
		dummyKoersClient = Mockito.mock(KoersClient.class);
		Mockito.when(dummyKoersClient.getDollarKoers()).thenReturn(BigDecimal.valueOf(1.5));
		euroService = new DefaultEuroService(dummyKoersClient);
	}
	@Test
	public void naarDollar() {
		assertEquals(0, BigDecimal.valueOf(3).compareTo(
				euroService.naarDollar(BigDecimal.valueOf(2))));
	}

}

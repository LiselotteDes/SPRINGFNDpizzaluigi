package be.vdab.pizzaluigi.repositories;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

// Een integration test van de DataSource

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataSourceTest {
	@Autowired
	private DataSource dataSource;	// import javax.sql.DataSource;
	@Test
	public void getConnection() throws SQLException {
		/*
		 * Je probeert een connectie te krijgen uit de DataSource.
		 * Als dit lukt is de DataSource correct geconfigureerd en slaagt de test.
		 */
		try (Connection connection = dataSource.getConnection()){
		}
	}

}

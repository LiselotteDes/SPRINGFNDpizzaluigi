package be.vdab.pizzaluigi.web;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
@Component
@SessionScope
class DefaultMandje implements Serializable, Mandje {
	private static final long serialVersionUID = 1L;
	/*
	 * Je onthoudt zo weinig mogelijk data in de session van de gebruiker.
	 * Je onthoudt daarom geen pizza's, enkel pizza id's.
	 */
	private final List<Long> pizzaIds = new ArrayList<>();
	
	@Override 
	public void addPizzaId(long pizzaId) {
		pizzaIds.add(pizzaId);
	}
	@Override
	public List<Long> getPizzaIds() {
		return pizzaIds;
	}
}

package be.vdab.pizzaluigi.web;
import java.util.List;
//Deze interface beschrijft de methods die je wil uitoeren op de data die je onthoudt in de session van de gebruiker.
public interface Mandje {
	void addPizzaId(long pizzaId);
	public List<Long> getPizzaIds();
}

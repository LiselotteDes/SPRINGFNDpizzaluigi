package be.vdab.pizzaluigi.web;
// Deze class dient als basis van het form object om pizza's toe te voegen aan het mandje.
class MandjeForm {
	private long pizzaId;

	public long getPizzaId() {
		return pizzaId;
	}

	public void setPizzaId(long pizzaId) {
		this.pizzaId = pizzaId;
	}
	
}

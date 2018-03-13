package be.vdab.pizzaluigi.entities;
import java.math.BigDecimal;
// Bean validation is een officiele Java (zie package) specificatie waarmee je objecten valideert.
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
public class Pizza {
	private long id;
	private String naam;
	// Beschrijft een validatie voor de variabele prijs: de inhoud is >=0 en <=99. (je kan bij 1 variabele meerdere validation annotations schrijven)
	@Min(0) @Max(99)
	private BigDecimal prijs;	// moet dus een waarde tussen 0 en 99 bevatten
	private boolean pikant;
	public Pizza(long id, String naam, BigDecimal prijs, boolean pikant) {
		this.id = id;
		this.naam = naam;
		this.prijs = prijs;
		this.pikant = pikant;
	}
	public Pizza() {
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNaam() {
		return naam;
	}
	public void setNaam(String naam) {
		this.naam = naam;
	}
	public BigDecimal getPrijs() {
		return prijs;
	}
	public void setPrijs(BigDecimal prijs) {
		this.prijs = prijs;
	}
	public boolean isPikant() {
		return pikant;
	}
	public void setPikant(boolean pikant) {
		this.pikant = pikant;
	}
}

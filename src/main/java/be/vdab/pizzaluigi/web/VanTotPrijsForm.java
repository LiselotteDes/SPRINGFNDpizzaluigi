package be.vdab.pizzaluigi.web;
import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
/*
 *  Form object class.
 *  Deze class is enkel nuttig als voorstelling van een HTML form 	==> class met package visibility in package be.vdab.pizzaluigi.web.
 *  (Als de class nuttig zou zijn in andere applicatie lagen 		==> class met public visibility  in package be.vdab.pizzaluigi.entities.)
 */
class VanTotPrijsForm {
	// Private variabele per invoervak in de HTML form (met bijbehorende getter en setter).
	@NotNull @Min(0)
	private BigDecimal van;
	@NotNull @Min(0)
	private BigDecimal tot;
	// Vereiste default constructor (automatisch doordat geen andere wordt gedefinieerd).
	public BigDecimal getVan() {
		return van;
	}
	public void setVan(BigDecimal van) {
		this.van = van;
	}
	public BigDecimal getTot() {
		return tot;
	}
	public void setTot(BigDecimal tot) {
		this.tot = tot;
	}
	
}

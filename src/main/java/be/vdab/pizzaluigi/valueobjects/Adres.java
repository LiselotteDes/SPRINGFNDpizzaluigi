package be.vdab.pizzaluigi.valueobjects;

public class Adres {
	private String straat;
	private String huisNr;
	private String postcode;
	private String gemeente;
	public Adres() {
	}
	public Adres(String straat, String huisNr, String postcode, String gemeente) {
		this.straat = straat;
		this.huisNr = huisNr;
		this.postcode = postcode;
		this.gemeente = gemeente;
	}
	public String getStraat() {
		return straat;
	}
	public String getHuisNr() {
		return huisNr;
	}
	public String getPostcode() {
		return postcode;
	}
	public String getGemeente() {
		return gemeente;
	}
}

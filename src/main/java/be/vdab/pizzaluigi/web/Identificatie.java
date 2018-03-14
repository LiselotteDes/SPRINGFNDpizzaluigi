package be.vdab.pizzaluigi.web;
// Deze interface beschrijft de methods die je wil uitoeren op de data die je onthoudt in de session van de gebruiker.
public interface Identificatie {
	String getEmailAdres();
	void setEmailAdres(String adres);
}

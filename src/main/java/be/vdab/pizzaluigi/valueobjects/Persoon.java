package be.vdab.pizzaluigi.valueobjects;
import javax.validation.Valid;
import org.hibernate.validator.constraints.Range;
// Werken volgens de JAVABEAN STANDAARD
public class Persoon {
	private String voornaam;
	private String familienaam;
	@Range(min = 0, max = 69)
	private int aantalKinderen;
	private boolean gehuwd;
	// Bean validation valideert bij de validatie van een Persoon object ook zijn Adres object en controleert dus of de postcode ligt tss 1000 en 9999.
	@Valid
	private Adres adres;
	// *** CONSTRUCTORS ***
	/*
	 * Een JavaBean moet een public default constructor hebben.
	 * Dit is het geval als je geen constructor tikte, 
	 * dan maakt de compiler een public default constructor.
	 * Je tikt nu een geparametriseerde constructor. 
	 * De compiler maakt dan geen default constructor meer, 
	 * dus moet je ook de default constructor zelf tikken.
	 */
	public Persoon() {	// default constructor
	}
	public Persoon(String voornaam, String familienaam, int aantalKinderen, boolean gehuwd, 
			Adres adres) {
		this.voornaam = voornaam;
		this.familienaam = familienaam;
		this.aantalKinderen = aantalKinderen;
		this.gehuwd = gehuwd;
		this.adres = adres;
	}
	// *** GETTERS & SETTERS ***
	/*
	 * De JavaBean standaard definieert dat je per attribuut de juiste getters maakt:
	 * - public methods
	 * - naam begint met get of is (ih geval van een boolean)
	 * - returntype is gelijk aan attribuut type
	 * - heeft geen parameters
	 * - retourneert de attribuut waarde uit de bijbehorende private variabele
	 */
	public String getVoornaam() {
		return voornaam;
	}
	public String getFamilienaam() {
		return familienaam;
	}
	public int getAantalKinderen() {
		return aantalKinderen;
	}
	public boolean isGehuwd() {
		return gehuwd;
	}
	public Adres getAdres() {
		return adres;
	}
	/*
	 * De JavaBean standaard definieert dat je per attribuut de juiste setters maakt:
	 * - naam begint met set 
	 * - returntype is void
	 * - heeft één parameter: de waarde die je wilt invullen ih attribuut
	 * - parametertype is gelijk aan het attribuut type
	 */
	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}
	public void setFamilienaam(String familienaam) {
		this.familienaam = familienaam;
	}
	public void setAantalKinderen(int aantalKinderen) {
		this.aantalKinderen = aantalKinderen;
	}
	public void setGehuwd(boolean gehuwd) {
		this.gehuwd = gehuwd;
	}
	public void setAdres(Adres adres) {
		this.adres = adres;
	}
	/*
	 * ReadOnly attributen:
	 * = Een attribuut dat enkel een getter (en geen setter) heeft,
	 * dus een attribuut dat je kan lezen, maar niet kan wijzigen.
	 * Een ReadOnly attribuut heeft niet altijd een eigen private variabele nodig, 
	 * zoals je hier ziet:
	 */
	public String getNaam() {
		return voornaam + ' ' + familienaam;
	}
}

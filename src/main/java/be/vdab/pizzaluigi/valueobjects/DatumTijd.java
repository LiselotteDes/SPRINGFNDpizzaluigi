package be.vdab.pizzaluigi.valueobjects;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
public class DatumTijd {
	@DateTimeFormat(style="SS")
	private final LocalDateTime waarde;
	public DatumTijd(LocalDateTime waarde) {
		this.waarde = waarde;
	}
	public LocalDateTime getWaarde() {
		return waarde;
	}
}

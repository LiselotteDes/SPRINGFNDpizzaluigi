package be.vdab.pizzaluigi.exceptions;

public class KoersClientException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public KoersClientException(String message) {
		super(message);
	}
}

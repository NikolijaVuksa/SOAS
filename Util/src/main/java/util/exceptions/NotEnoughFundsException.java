package util.exceptions;

public class NotEnoughFundsException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotEnoughFundsException() {
		
	}
	
	public NotEnoughFundsException(String message) {
		super(message);
	}
}

package util.exceptions;

public class InvalidConversionResultException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidConversionResultException() {
		
	}
	
	public InvalidConversionResultException(String message) {
		super(message);
	}
}
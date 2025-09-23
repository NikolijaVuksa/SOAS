package util.exceptions;


public class WalletNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WalletNotFoundException() {
		
	}
	
	public WalletNotFoundException(String message) {
		super(message);
	}
}
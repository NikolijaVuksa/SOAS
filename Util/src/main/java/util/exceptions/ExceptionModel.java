package util.exceptions;

import org.springframework.http.HttpStatus;

public class ExceptionModel {

	private String errorMessage;
	private String recommandation;
	private HttpStatus status;
	
	public ExceptionModel () {
		
	}
	
	public ExceptionModel(String errorMessage, String recommandation, HttpStatus status) {
		this.errorMessage = errorMessage;
		this.recommandation = recommandation;
		this.status = status;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getRecommandation() {
		return recommandation;
	}
	public void setRecommandation(String recommandation) {
		this.recommandation = recommandation;
	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
}

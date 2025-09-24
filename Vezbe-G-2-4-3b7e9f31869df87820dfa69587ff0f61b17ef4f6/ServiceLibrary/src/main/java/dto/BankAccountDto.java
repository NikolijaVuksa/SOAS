package dto;

import java.math.BigDecimal;

public class BankAccountDto {
	
	private String email;
	  
	private BigDecimal EUR, USD, CHF, GBP, CAD, RSD;
	
	private String message;
	
	public BankAccountDto() {
		
	}
	 
	public BankAccountDto(String email) {
        this.email = email;
        this.EUR = BigDecimal.ZERO;
        this.USD = BigDecimal.ZERO;
        this.RSD = BigDecimal.ZERO;
        this.CHF = BigDecimal.ZERO;
        this.CAD = BigDecimal.ZERO;
        this.GBP = BigDecimal.ZERO;
    }
	
	
	
	public BankAccountDto(String email, BigDecimal EUR, BigDecimal USD, BigDecimal CHF, BigDecimal GBP, BigDecimal CAD,
			BigDecimal RSD) {
		super();
		this.email = email;
		this.EUR = EUR;
		this.USD = USD;
		this.CHF = CHF;
		this.GBP = GBP;
		this.CAD = CAD;
		this.RSD = RSD;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getEUR() {
		return EUR;
	}

	public void setEUR(BigDecimal EUR) {
		this.EUR = EUR;
	}

	public BigDecimal getUSD() {
		return USD;
	}

	public void setUSD(BigDecimal USD) {
		this.USD = USD;
	}

	public BigDecimal getCHF() {
		return CHF;
	}

	public void setCHF(BigDecimal CHF) {
		this.CHF = CHF;
	}

	public BigDecimal getGBP() {
		return GBP;
	}

	public void setGBP(BigDecimal GBP) {
		this.GBP = GBP;
	}

	public BigDecimal getCAD() {
		return CAD;
	}

	public void setCAD(BigDecimal CAD) {
		this.CAD = CAD;
	}

	public BigDecimal getRSD() {
		return RSD;
	}

	public void setRSD(BigDecimal RSD) {
		this.RSD = RSD;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
        this.message = message;		
	}
	
	
	public boolean hasEnoughBalance(String currency, BigDecimal amount) {
	    switch (currency) {
	        case "EUR":
	            return this.EUR.compareTo(amount) >= 0;
	        case "USD":
	            return this.USD.compareTo(amount) >= 0;
	        case "CHF":
	            return this.CHF.compareTo(amount) >= 0;
	        case "GBP":
	            return this.GBP.compareTo(amount) >= 0;
	        case "CAD":
	            return this.CAD.compareTo(amount) >= 0;
	        case "RSD":
	            return this.RSD.compareTo(amount) >= 0;
	        default:
	        	return false;
	    }
	}


}


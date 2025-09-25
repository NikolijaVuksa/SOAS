package dto;

import java.math.BigDecimal;

public class CryptoWalletDto {

	private String email;
	private String message;
	private BigDecimal BTC, ETH, UST;
	
	public CryptoWalletDto() {
		
	}
	 
	public CryptoWalletDto(String email) {
        this.email = email;
        this.BTC = BigDecimal.ZERO;
        this.ETH = BigDecimal.ZERO;
        this.UST = BigDecimal.ZERO;
    }
	
	
	public CryptoWalletDto(String email, BigDecimal BTC, BigDecimal ETH, BigDecimal UST) {
		super();
		this.email = email;
		this.BTC = BTC;
		this.ETH = ETH;
		this.UST = UST;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getBTC() {
		return BTC;
	}

	public void setBTC(BigDecimal BTC) {
		this.BTC = BTC;
	}

	public BigDecimal getETH() {
		return ETH;
	}

	public void setETH(BigDecimal ETH) {
		this.ETH = ETH;
	}

	public BigDecimal getUST() {
		return UST;
	}

	public void setUST(BigDecimal UST) {
		this.UST = UST;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
        this.message = message;		
	}
	
	
	public boolean hasEnoughBalance(String currency, BigDecimal amount) {
	    switch (currency.toUpperCase()) {
	        case "BTC":
	            return this.BTC.compareTo(amount) >= 0;
	        case "ETH":
	            return this.ETH.compareTo(amount) >= 0;
	        case "UST":
	            return this.UST.compareTo(amount) >= 0;
	        default:
	        	return false;
	    }
	}
	
	public void subtractBalance(String currency, BigDecimal quantity) {
		switch (currency) {
        case "BTC": this.setBTC(this.getBTC().subtract(quantity)); break;
        case "ETH": this.setETH(this.getETH().subtract(quantity)); break;
        case "UST": this.setUST(this.getUST().subtract(quantity)); break;
    }

   
	}
	
	public void addBalance(String currency, BigDecimal convertedAmount) {
		switch (currency) {
	        case "BTC": this.setBTC(this.getBTC().add(convertedAmount)); break;
	        case "ETH": this.setETH(this.getETH().add(convertedAmount)); break;
	        case "UST": this.setUST(this.getUST().add(convertedAmount)); break;
	    }
	}


	
}

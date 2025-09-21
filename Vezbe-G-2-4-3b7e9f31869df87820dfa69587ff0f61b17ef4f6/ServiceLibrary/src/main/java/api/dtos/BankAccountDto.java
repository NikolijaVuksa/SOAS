package api.dtos;

import java.math.BigDecimal;

public class BankAccountDto {
	
	private String email;
	  
	private BigDecimal eur, usd, chf, gbp, cad, rsd;
	
	public BankAccountDto() {
		
	}
	 
	public BankAccountDto(String email) {
        this.email = email;
        this.eur = BigDecimal.ZERO;
        this.usd = BigDecimal.ZERO;
        this.rsd = BigDecimal.ZERO;
        this.chf = BigDecimal.ZERO;
        this.cad = BigDecimal.ZERO;
        this.gbp = BigDecimal.ZERO;
    }
	
	
	
	public BankAccountDto(String email, BigDecimal eur, BigDecimal usd, BigDecimal chf, BigDecimal gbp, BigDecimal cad,
			BigDecimal rsd) {
		super();
		this.email = email;
		this.eur = eur;
		this.usd = usd;
		this.chf = chf;
		this.gbp = gbp;
		this.cad = cad;
		this.rsd = rsd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getEur() {
		return eur;
	}

	public void setEur(BigDecimal eur) {
		this.eur = eur;
	}

	public BigDecimal getUsd() {
		return usd;
	}

	public void setUsd(BigDecimal usd) {
		this.usd = usd;
	}

	public BigDecimal getChf() {
		return chf;
	}

	public void setChf(BigDecimal chf) {
		this.chf = chf;
	}

	public BigDecimal getGbp() {
		return gbp;
	}

	public void setGbp(BigDecimal gbp) {
		this.gbp = gbp;
	}

	public BigDecimal getCad() {
		return cad;
	}

	public void setCad(BigDecimal cad) {
		this.cad = cad;
	}

	public BigDecimal getRsd() {
		return rsd;
	}

	public void setRsd(BigDecimal rsd) {
		this.rsd = rsd;
	}

}


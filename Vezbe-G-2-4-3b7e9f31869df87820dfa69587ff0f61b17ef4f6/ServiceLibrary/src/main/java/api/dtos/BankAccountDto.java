package api.dtos;

public class BankAccountDto {
	
	private String email;
	  
	private double eur, usd, chf, gbp, cad, rsd;
	 
	public BankAccountDto() {
		
	}
	
    public BankAccountDto(String email, double eur, double usd, double chf, double gbp, double cad, double rsd) {
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

	public double getEur() {
		return eur;
	}

	public void setEur(double eur) {
		this.eur = eur;
	}

	public double getUsd() {
		return usd;
	}

	public void setUsd(double usd) {
		this.usd = usd;
	}

	public double getChf() {
		return chf;
	}

	public void setChf(double chf) {
		this.chf = chf;
	}

	public double getGbp() {
		return gbp;
	}

	public void setGbp(double gbp) {
		this.gbp = gbp;
	}

	public double getCad() {
		return cad;
	}

	public void setCad(double cad) {
		this.cad = cad;
	}

	public double getRsd() {
		return rsd;
	}

	public void setRsd(double rsd) {
		this.rsd = rsd;
	}

}


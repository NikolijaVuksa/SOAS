package bankAccountService;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "bank_account")
public class BankAccountModel {
	
	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE,generator="my_bank_seq")
	@SequenceGenerator(name="my_bank_seq", sequenceName="my_bank_seq", allocationSize=1, initialValue=1)
	private int id;
	

	@Column(nullable = false, unique = true)
	private String email; 
	
	@Column(nullable = false) 
	private double eur = 0;
	
	@Column(nullable = false) 
	private double usd = 0;
	
	@Column(nullable = false) 
	private double chf = 0;
	
	@Column(nullable = false) 
	private double gbp = 0;
	
	@Column(nullable = false) 
	private double cad = 0;
	
	@Column(nullable = false) 
	private double rsd = 0;
	
	public BankAccountModel() {
		
	}
	
	public BankAccountModel(String email, double eur, double usd, double chf, double gbp, double cad, double rsd) {
		super();
		this.email = email;
		this.eur = eur;
		this.usd = usd;
		this.chf = chf;
		this.gbp = gbp;
		this.cad = cad;
		this.rsd = rsd;
	}
	public BankAccountModel(int id, String email, double eur, double usd, double chf, double gbp, double cad,
			double rsd) {
		super();
		this.id = id;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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


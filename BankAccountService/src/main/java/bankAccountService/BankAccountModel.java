package bankAccountService;

import java.math.BigDecimal;

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
	@GeneratedValue(strategy= GenerationType.SEQUENCE,generator="my_acc_seq")
	@SequenceGenerator(name="my_acc_seq", sequenceName="my_acc_seq", allocationSize=1, initialValue=2)
	private int id;
	
	
	@Column(nullable = false, unique = true)
    private String email;

	private BigDecimal eur = BigDecimal.ZERO;
    private BigDecimal usd = BigDecimal.ZERO;
    private BigDecimal chf = BigDecimal.ZERO;
    private BigDecimal gbp = BigDecimal.ZERO;
    private BigDecimal cad = BigDecimal.ZERO;
    private BigDecimal rsd = BigDecimal.ZERO;
	
	public BankAccountModel() {
		
	}
	
	public BankAccountModel(String email) {
		this.email = email;
	}
	public BankAccountModel(int id, String email, BigDecimal eur, BigDecimal usd, BigDecimal chf, BigDecimal gbp, BigDecimal cad,
			BigDecimal rsd) {
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
	
	public BankAccountModel(String email, BigDecimal eur, BigDecimal usd, BigDecimal chf, BigDecimal gbp,
			BigDecimal cad, BigDecimal rsd) {
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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


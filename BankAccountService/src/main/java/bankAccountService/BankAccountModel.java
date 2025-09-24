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

	private BigDecimal EUR = BigDecimal.ZERO;
    private BigDecimal USD = BigDecimal.ZERO;
    private BigDecimal CHF = BigDecimal.ZERO;
    private BigDecimal GBP = BigDecimal.ZERO;
    private BigDecimal CAD = BigDecimal.ZERO;
    private BigDecimal RSD = BigDecimal.ZERO;
	
	public BankAccountModel() {
		
	}
	
	public BankAccountModel(String email) {
		this.email = email;
	}
	public BankAccountModel(int id, String email, BigDecimal EUR, BigDecimal USD, BigDecimal CHF, BigDecimal GBP, BigDecimal CAD,
			BigDecimal RSD) {
		super();
		this.id = id;
		this.email = email;
		this.EUR = EUR;
		this.USD = USD;
		this.CHF = CHF;
		this.GBP = GBP;
		this.CAD = CAD;
		this.RSD = RSD;
	}
	
	public BankAccountModel(String email, BigDecimal EUR, BigDecimal USD, BigDecimal CHF, BigDecimal GBP,
			BigDecimal CAD, BigDecimal RSD) {
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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

}


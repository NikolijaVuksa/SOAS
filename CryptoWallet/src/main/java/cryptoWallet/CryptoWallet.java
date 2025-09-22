package cryptoWallet;


import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "wallet")
public class CryptoWallet {

	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE,generator="my_wall_seq")
	@SequenceGenerator(name="my_wall_seq", sequenceName="my_wall_seq", allocationSize=1, initialValue=2)
	private int id;
	
	
	@Column(nullable = false, unique = true)
    private String email;

    private BigDecimal BTC = BigDecimal.ZERO;
    private BigDecimal ETH = BigDecimal.ZERO;
    private BigDecimal UST = BigDecimal.ZERO; 

    public CryptoWallet() {}
    
    public CryptoWallet(String email) {
    	this.email = email;
    }
    
    public CryptoWallet(int id, String email, BigDecimal BTC, BigDecimal ETH, BigDecimal UST) {
    	this.id = id;
        this.email = email;
        this.BTC = BTC;
        this.ETH = ETH;
        this.UST = UST;
    }

    public CryptoWallet(String email, BigDecimal BTC, BigDecimal ETH, BigDecimal UST) {
        this.email = email;
        this.BTC = BTC;
        this.ETH = ETH;
        this.UST = UST;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

}


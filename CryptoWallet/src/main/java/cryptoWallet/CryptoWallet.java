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

    private BigDecimal btc = BigDecimal.ZERO;
    private BigDecimal eth = BigDecimal.ZERO;
    private BigDecimal usdt = BigDecimal.ZERO; 

    public CryptoWallet() {}
    
    public CryptoWallet(String email) {
    	this.email = email;
    }
    
    public CryptoWallet(int id, String email, BigDecimal btc, BigDecimal eth, BigDecimal usdt) {
    	this.id = id;
        this.email = email;
        this.btc = btc;
        this.eth = eth;
        this.usdt = usdt;
    }

    public CryptoWallet(String email, BigDecimal btc, BigDecimal eth, BigDecimal usdt) {
        this.email = email;
        this.btc = btc;
        this.eth = eth;
        this.usdt = usdt;
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

	public BigDecimal getBtc() {
		return btc;
	}

	public void setBtc(BigDecimal btc) {
		this.btc = btc;
	}

	public BigDecimal getEth() {
		return eth;
	}

	public void setEth(BigDecimal eth) {
		this.eth = eth;
	}

	public BigDecimal getUsdt() {
		return usdt;
	}

	public void setUsdt(BigDecimal usdt) {
		this.usdt = usdt;
	}

}


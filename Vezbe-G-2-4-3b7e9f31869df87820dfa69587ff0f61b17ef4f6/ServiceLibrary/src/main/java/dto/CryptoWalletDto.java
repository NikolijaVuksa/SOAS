package dto;

import java.math.BigDecimal;

public class CryptoWalletDto {

	private String email;
	  
	private BigDecimal btc, eth, usdt;
	
	public CryptoWalletDto() {
		
	}
	 
	public CryptoWalletDto(String email) {
        this.email = email;
        this.btc = BigDecimal.ZERO;
        this.eth = BigDecimal.ZERO;
        this.usdt = BigDecimal.ZERO;
    }
	
	
	public CryptoWalletDto(String email, BigDecimal btc, BigDecimal eth, BigDecimal usdt) {
		super();
		this.email = email;
		this.btc = btc;
		this.eth = eth;
		this.usdt = usdt;
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

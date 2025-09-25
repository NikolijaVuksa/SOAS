package tradeCurrency;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "trade_currency")
public class TradeCurrencyModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private int id;
	
	@Column(name = "trade_from")
	private String from;
	@Column(name = "trade_to")
	private String to;
	private BigDecimal tradeRate;
	
	public TradeCurrencyModel() {}

	public TradeCurrencyModel(int id, String from, String to, BigDecimal tradeRate) {
		super();
		this.id = id;
		this.from = from;
		this.to = to;
		this.tradeRate = tradeRate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}


	public BigDecimal getTradeRate() {
		return tradeRate;
	}

	public void setTradeRate(BigDecimal tradeRate) {
		this.tradeRate = tradeRate;
	}
	
	

}

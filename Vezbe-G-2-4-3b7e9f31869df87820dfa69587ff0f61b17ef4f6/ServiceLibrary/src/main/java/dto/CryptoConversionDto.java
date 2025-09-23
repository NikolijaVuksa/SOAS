package dto;

import java.math.BigDecimal;


public class CryptoConversionDto {
	private CryptoExchangeDto exchange;
	private BigDecimal quantity;
	private ConversionResult conversionResult;
	private boolean feign;
	private String message;

	public CryptoConversionDto() {

	}
	
	public CryptoConversionDto(CryptoExchangeDto exchange, BigDecimal quantity) {
		this.exchange = exchange;
		this.quantity = quantity;
		CryptoConversionDto.ConversionResult result = 
				new CryptoConversionDto.ConversionResult(exchange.getTo(), quantity.multiply(exchange.getExchangeRate()));
		this.conversionResult = result;
	}

	public CryptoExchangeDto getExchange() {
		return exchange;
	}

	public void setExchange(CryptoExchangeDto exchange) {
		this.exchange = exchange;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public ConversionResult getConversionResult() {
		return conversionResult;
	}

	public void setConversionResult(ConversionResult conversionResult) {
		this.conversionResult = conversionResult;
	}
	

	public boolean isFeign() {
		return feign;
	}

	public void setFeign(boolean feign) {
		this.feign = feign;
	}


	public class ConversionResult {
		private String to;
		private BigDecimal convertedAmount;

		public ConversionResult() {

		}

		public ConversionResult(String to, BigDecimal convertedAmount) {
			this.to = to;
			this.convertedAmount = convertedAmount;
		}

		public String getTo() {
			return to;
		}

		public void setTo(String to) {
			this.to = to;
		}

		public BigDecimal getConvertedAmount() {
			return convertedAmount;
		}

		public void setConvertedAmount(BigDecimal convertedAmount) {
			this.convertedAmount = convertedAmount;
		}

	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
        this.message = message;		
	}
}

package currencyConversion;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import api.proxies.BankAccountProxy;
import api.proxies.CurrencyExchangeProxy;
import api.services.CurrencyConversionService;
import dto.BankAccountDto;
import dto.CryptoConversionDto;
import dto.CryptoWalletDto;
import dto.CurrencyConversionDto;
import dto.CurrencyExchangeDto;
import feign.FeignException;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.Retry;
import util.exceptions.BankAccountNotFoundException;
import util.exceptions.InvalidConversionResultException;
import util.exceptions.InvalidQuantityException;
import util.exceptions.NotEnoughFundsException;

@RestController
public class CurrencyConversionServiceImpl implements CurrencyConversionService {

	private RestTemplate template = new RestTemplate();
	
	@Autowired
	private CurrencyExchangeProxy proxy;
	
	@Autowired 
	private BankAccountProxy accProxy;
	
	Retry retry; 
	CurrencyExchangeDto response;
	
	public CurrencyConversionServiceImpl(RetryRegistry registry) {
		retry = registry.retry("default");
	}

	@Override
	@CircuitBreaker(name = "cb", fallbackMethod = "fallback")
	public ResponseEntity<?> getConversionFeign(String email, String from, String to, BigDecimal quantity) {
		/*if(quantity.compareTo(BigDecimal.valueOf(300,0)) == 1){
			throw new InvalidQuantityException(String.format("Quantity of %s is too large", quantity));
		}
		
		retry.executeSupplier(() -> response = proxy.getExchangeFeign(from,to).getBody());

		CurrencyConversionDto finalResponse = new CurrencyConversionDto(response, quantity);
		finalResponse.setFeign(true);
		
		return ResponseEntity.ok(finalResponse);*/
		if(quantity.compareTo(BigDecimal.valueOf(300,0)) == 1){
			throw new InvalidQuantityException(String.format("Quantity of %s is too large", quantity));
		}
		
		ResponseEntity<BankAccountDto> account = accProxy.getMyAccount(email);
		BankAccountDto accountDto = account.getBody();
		
		if (accountDto == null) {
		    throw new BankAccountNotFoundException(String.format("Bank account doesn't exist for user with email: %s", email));
		}
		
		if (!accountDto.hasEnoughBalance(from, quantity)) {
		    throw new NotEnoughFundsException(String.format("Not enough funds in account to exchange %s to %s: ", from, to));
		}
		
		retry.executeSupplier(() -> response = proxy.getExchangeFeign(from,to).getBody());

		CurrencyConversionDto conversionResponse = new CurrencyConversionDto(response, quantity);
		conversionResponse.setFeign(true);
		BigDecimal convertedAmount = conversionResponse.getConversionResult().getConvertedAmount();
		
		if (convertedAmount.compareTo(BigDecimal.ZERO) == 0)
		{
			throw new InvalidConversionResultException(String.format("Conversion from %s to %s resulted in 0. Transaction not allowed.", from, to));		
		}
		
		switch (from) {
	        case "EUR": accountDto.setEUR(accountDto.getEUR().subtract(quantity)); break;
	        case "USD": accountDto.setUSD(accountDto.getUSD().subtract(quantity)); break;
	        case "CHF": accountDto.setCHF(accountDto.getCHF().subtract(quantity)); break;
	        case "GBP": accountDto.setGBP(accountDto.getGBP().subtract(quantity)); break;
	        case "CAD": accountDto.setCAD(accountDto.getCAD().subtract(quantity)); break;
	        case "RSD": accountDto.setRSD(accountDto.getRSD().subtract(quantity)); break;
	    }

	    switch (to) {
	        case "EUR": accountDto.setEUR(accountDto.getEUR().add(convertedAmount)); break;
	        case "USD": accountDto.setUSD(accountDto.getUSD().add(convertedAmount)); break;
	        case "CHF": accountDto.setCHF(accountDto.getCHF().add(convertedAmount)); break;
	        case "GBP": accountDto.setGBP(accountDto.getGBP().add(convertedAmount)); break;
	        case "CAD": accountDto.setCAD(accountDto.getCAD().add(convertedAmount)); break;
	        case "RSD": accountDto.setRSD(accountDto.getRSD().add(convertedAmount)); break;
	    }

	    accProxy.updateAccount(accountDto);
	    
	    accountDto.setMessage(String.format("Successfully exchanged %s %s for %s %s.", quantity, from, convertedAmount, to));

	    return ResponseEntity.ok(accountDto);
	}
	
	public ResponseEntity<?> fallback(CallNotPermittedException ex){
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
				.body("Currency conversion service is currently unavailbale, Circuit breaker is in OPEN state!");
	}
	
	@Override
	public ResponseEntity<?> getConversion(String from, String to, BigDecimal quantity) {
		if(quantity.compareTo(BigDecimal.valueOf(300,0)) == 1){
			throw new InvalidQuantityException(String.format("Quantity of %s is too large", quantity));
		}
		String endPoint = "http://localhost:8000/currency-exchange?from=" + from + "&to=" + to;
		ResponseEntity<CurrencyExchangeDto> response = template.getForEntity(endPoint, CurrencyExchangeDto.class);		
		return ResponseEntity.ok(new CurrencyConversionDto(response.getBody(), quantity));
	}

}

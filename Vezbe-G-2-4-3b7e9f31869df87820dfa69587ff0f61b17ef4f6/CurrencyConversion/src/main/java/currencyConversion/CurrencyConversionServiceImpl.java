package currencyConversion;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import api.proxies.BankAccountProxy;
import api.proxies.CurrencyExchangeProxy;
import api.services.CurrencyConversionService;
import dto.BankAccountDto;
import dto.CurrencyConversionDto;
import dto.CurrencyExchangeDto;
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
	public ResponseEntity<?> getConversionFeign(String email, String from, String to, BigDecimal quantity, String internalCall) {
		boolean isInternal = "true".equalsIgnoreCase(internalCall);

	    if (!isInternal && quantity.compareTo(BigDecimal.valueOf(300)) > 0) {
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
		
		accountDto.subtractBalance(from, quantity);
		accountDto.addBalance(to, convertedAmount);


	    accProxy.updateAccount(accountDto);
	    
	    accountDto.setMessage(String.format("Successfully exchanged %s %s for %s %s.", quantity, from, convertedAmount, to));

	    return ResponseEntity.ok(accountDto);
	}
	
	public ResponseEntity<?> fallback(CallNotPermittedException ex){
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
				.body("Currency conversion service is currently unavailbale, Circuit breaker is in OPEN state!");
	}
	
	@Override
	public ResponseEntity<CurrencyConversionDto> getConversion(String from,  String to, BigDecimal quantity, String internalCall){
		boolean isInternal = "true".equalsIgnoreCase(internalCall);

	    if (!isInternal && quantity.compareTo(BigDecimal.valueOf(300)) > 0) {
            throw new InvalidQuantityException(String.format("Quantity of %s is too large", quantity));
        }

        ResponseEntity<CurrencyExchangeDto> response = proxy.getExchangeFeign(from, to);
        
        return ResponseEntity.ok(new CurrencyConversionDto(response.getBody(), quantity));
	}

}

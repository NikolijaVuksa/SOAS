package cryptoConversion;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import api.proxies.CryptoExchangeProxy;
import api.services.CryptoConversionService;
import dto.CryptoConversionDto;
import dto.CryptoExchangeDto;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import util.exceptions.InvalidQuantityException;

@RestController
public class CryptoConversionServiceImpl implements CryptoConversionService{
	
	private RestTemplate template = new RestTemplate(); // promeniti jer nije dozvoljeno
	
	@Autowired
	private CryptoExchangeProxy proxy;
	
	Retry retry; 
	CryptoExchangeDto response;
	
	public CryptoConversionServiceImpl(RetryRegistry registry) {
		retry = registry.retry("default");
	}

	@Override
	@CircuitBreaker(name = "cb", fallbackMethod = "fallback")
	public ResponseEntity<?> getCryptoConversionFeign(String from, String to, BigDecimal quantity) {
		if(quantity.compareTo(BigDecimal.valueOf(300,0)) == 1){
			throw new InvalidQuantityException(String.format("Quantity of %s is too large", quantity));
		}
		
		retry.executeSupplier(() -> response = proxy.getCryptoExchangeFeign(from,to).getBody());

		CryptoConversionDto finalResponse = new CryptoConversionDto(response, quantity);
		finalResponse.setFeign(true);
		
		return ResponseEntity.ok(finalResponse);
	}
	
	public ResponseEntity<?> fallback(CallNotPermittedException ex){
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
				.body("Crypto conversion service is currently unavailbale, Circuit breaker is in OPEN state!");
	}
	
	@Override
	public ResponseEntity<?> getCryptoConversion(String from, String to, BigDecimal quantity) {
		if(quantity.compareTo(BigDecimal.valueOf(300,0)) == 1){
			throw new InvalidQuantityException(String.format("Quantity of %s is too large", quantity));
		}
		String endPoint = "http://localhost:8400/crypto-exchange?from=" + from + "&to=" + to;
		ResponseEntity<CryptoExchangeDto> response = template.getForEntity(endPoint, CryptoExchangeDto.class);		
		return ResponseEntity.ok(new CryptoConversionDto(response.getBody(), quantity));
	}


}

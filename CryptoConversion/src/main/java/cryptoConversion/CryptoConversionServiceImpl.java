package cryptoConversion;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

import api.proxies.CryptoExchangeProxy;
import api.proxies.CryptoWalletProxy;
import api.services.CryptoConversionService;
import dto.CryptoConversionDto;
import dto.CryptoExchangeDto;
import dto.CryptoWalletDto;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import util.exceptions.InvalidConversionResultException;
import util.exceptions.InvalidQuantityException;
import util.exceptions.NotEnoughFundsException;
import util.exceptions.WalletNotFoundException;

@RestController
public class CryptoConversionServiceImpl implements CryptoConversionService{
	
	private RestTemplate template = new RestTemplate(); // promeniti jer nije dozvoljeno
	
	@Autowired
	private CryptoExchangeProxy proxy;
	
	@Autowired
	private CryptoWalletProxy walletProxy;
	
	
	Retry retry; 
	CryptoExchangeDto response;
	
	public CryptoConversionServiceImpl(RetryRegistry registry) {
		retry = registry.retry("default");
	}

	@Override
	@CircuitBreaker(name = "cb", fallbackMethod = "fallback")
	public ResponseEntity<CryptoWalletDto> getCryptoConversionFeign(String email, String from, String to, BigDecimal quantity) {
		if(quantity.compareTo(BigDecimal.valueOf(300,0)) == 1){
			throw new InvalidQuantityException(String.format("Quantity of %s is too large", quantity));
		}
		
		ResponseEntity<CryptoWalletDto> wallet = walletProxy.getMyWallet(email);
		CryptoWalletDto walletDto = wallet.getBody();
		
		if (walletDto == null) {
		    throw new WalletNotFoundException(String.format("Bank account doesn't exist for user with email: %s", email));
		}
		
		if (!walletDto.hasEnoughBalance(from, quantity)) {
		    throw new NotEnoughFundsException(String.format("Not enough funds in wallet to exchange %s to %s", from, to));
		}
		
		retry.executeSupplier(() -> response = proxy.getCryptoExchangeFeign(from,to).getBody());

		CryptoConversionDto conversionResponse = new CryptoConversionDto(response, quantity);
		conversionResponse.setFeign(true);
		BigDecimal convertedAmount = conversionResponse.getConversionResult().getConvertedAmount();
		
		if (convertedAmount.compareTo(BigDecimal.ZERO) == 0)
		{
			throw new InvalidConversionResultException(String.format("Conversion from %s to %s resulted in 0. Transaction not allowed.", from, to));		
		}
		
		switch (from) {
	        case "BTC": walletDto.setBTC(walletDto.getBTC().subtract(quantity)); break;
	        case "ETH": walletDto.setETH(walletDto.getETH().subtract(quantity)); break;
	        case "UST": walletDto.setUST(walletDto.getUST().subtract(quantity)); break;
	    }

	    switch (to) {
	        case "BTC": walletDto.setBTC(walletDto.getBTC().add(convertedAmount)); break;
	        case "ETH": walletDto.setETH(walletDto.getETH().add(convertedAmount)); break;
	        case "UST": walletDto.setUST(walletDto.getUST().add(convertedAmount)); break;
	    }

	    walletProxy.updateWallet(walletDto);
	    
	    walletDto.setMessage(String.format("Successfully exchanged %s %s for %s %s.", quantity, from, convertedAmount, to));

	    return ResponseEntity.ok(walletDto);
		
	}
	
	public ResponseEntity<?> fallback(CallNotPermittedException ex){
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
				.body("Crypto conversion service is currently unavailbale, Circuit breaker is in OPEN state!");
	}
	
	@Override
	public ResponseEntity<?> getCryptoConversion(String email,String from, String to, BigDecimal quantity) {
		if(quantity.compareTo(BigDecimal.valueOf(300,0)) == 1){
			throw new InvalidQuantityException(String.format("Quantity of %s is too large", quantity));
		}
		String endPoint = "http://localhost:8400/crypto-exchange?from=" + from + "&to=" + to;
		ResponseEntity<CryptoExchangeDto> response = template.getForEntity(endPoint, CryptoExchangeDto.class);		
		return ResponseEntity.ok(new CryptoConversionDto(response.getBody(), quantity));
	}


}
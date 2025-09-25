package tradeCurrency;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import api.proxies.BankAccountProxy;
import api.proxies.CryptoConversionProxy;
import api.proxies.CryptoExchangeProxy;
import api.proxies.CryptoWalletProxy;
import api.proxies.CurrencyConversionProxy;
import api.proxies.CurrencyExchangeProxy;
import api.services.TradeCurrencyService;
import dto.BankAccountDto;
import dto.CryptoWalletDto;
import dto.CurrencyConversionDto;
import util.exceptions.BankAccountNotFoundException;
import util.exceptions.CurrencyDoesntExistException;
import util.exceptions.InvalidConversionResultException;
import util.exceptions.InvalidQuantityException;
import util.exceptions.NotEnoughFundsException;
import util.exceptions.WalletNotFoundException;

@RestController
public class TradeCurrencyImpl implements TradeCurrencyService{
	
	@Autowired
	private BankAccountProxy accountProxy;
	
	@Autowired
	private CryptoWalletProxy walletProxy;
	
	@Autowired 
	private CurrencyExchangeProxy fiatProxy;
	
	@Autowired 
	private CryptoExchangeProxy cryptoProxy;
	
	@Autowired
	private CurrencyConversionProxy fiatConversionProxy;
		
	@Autowired 
	private TradeCurrencyRepository repo;
	
	
	@Override
	public ResponseEntity<?> getTradeFeign(String email, String from, String to, BigDecimal quantity) {
		if(quantity.compareTo(BigDecimal.valueOf(300,0)) == 1){
			throw new InvalidQuantityException(String.format("Quantity of %s is too large", quantity));
		}
		
		 List<String> fiatCurrencies = fiatProxy.getAllCurrencies().getBody();
		 List<String> cryptoCurrencies = cryptoProxy.getAllCurrencies().getBody();

		 List<String> allCurrencies = new ArrayList();
		 allCurrencies.addAll(fiatCurrencies);
		 allCurrencies.addAll(cryptoCurrencies);
		 
		 if (!allCurrencies.contains(from) || !allCurrencies.contains(to)) {
			    throw new CurrencyDoesntExistException(
			        String.format("Unsupported conversion: %s → %s", from, to),
			        allCurrencies
			    );
			}
		
		if (fiatProxy.getAllCurrencies().getBody().contains(from)) {
			return tradeFiatToCrypto(email, from, to, quantity);
		}
		
		if (cryptoProxy.getAllCurrencies().getBody().contains(from)) {
			return tradeCryptoToFiat(email, from, to, quantity);
		}
		
		throw new CurrencyDoesntExistException(
		    String.format("Unsupported conversion: %s → %s. Only FIAT ↔ CRYPTO conversions are allowed.", from, to), allCurrencies);	
		}
	
	

	private ResponseEntity<BankAccountDto> tradeCryptoToFiat(String email, String from, String to, BigDecimal quantity) {
	    ResponseEntity<BankAccountDto> account = accountProxy.getMyAccount(email);
	    BankAccountDto accountDto = account.getBody();

	    ResponseEntity<CryptoWalletDto> wallet = walletProxy.getMyWallet(email);
	    CryptoWalletDto walletDto = wallet.getBody();

	    if (accountDto == null) {
	        throw new BankAccountNotFoundException(String.format("Bank account doesn't exist for user with email: %s", email));
	    }

	    if (walletDto == null) {
	        throw new WalletNotFoundException(String.format("Crypto wallet doesn't exist for user with email: %s", email));
	    }

	    if (!walletDto.hasEnoughBalance(from, quantity)) {
	        throw new NotEnoughFundsException(String.format("Not enough %s in wallet to exchange %s to %s.", from, quantity, to));
	    }

	    TradeCurrencyModel rate = repo.findByFromAndTo(from, "EUR");
	    if (rate == null) {
	        throw new IllegalArgumentException("No trade rate for " + from + " → EUR");
	    }

	    BigDecimal bridgeQuantity = quantity.multiply(rate.getTradeRate());
	    String bridgeCurrency = "EUR";

	    BigDecimal finalAmount = bridgeQuantity;
	    if (!to.equals("EUR") && !to.equals("USD")) {
	        ResponseEntity<CurrencyConversionDto> finalResult = fiatConversionProxy.getConversion(bridgeCurrency, to, bridgeQuantity, "true");
	        CurrencyConversionDto finalResponse = finalResult.getBody();
	        finalAmount = finalResponse.getConversionResult().getConvertedAmount();
	        bridgeCurrency = to;
	    }
	    
	    BigDecimal fiatAmount = bridgeQuantity.multiply(rate.getTradeRate());

		if (fiatAmount.compareTo(BigDecimal.ZERO) == 0)
		{
			throw new InvalidConversionResultException(String.format("Conversion from %s to %s resulted in 0. Transaction not allowed.", from, to));		
		}
		

	    walletDto.subtractBalance(from, quantity);
	    accountDto.addBalance(to, finalAmount);

	    walletProxy.updateWallet(walletDto);
	    accountProxy.updateAccount(accountDto);

	    accountDto.setMessage(String.format("Successfully exchanged %s %s for %s %s.", quantity, from, finalAmount, to));

	    return ResponseEntity.ok(accountDto);
	}

	private ResponseEntity<CryptoWalletDto> tradeFiatToCrypto(String email, String from, String to, BigDecimal quantity) {
		ResponseEntity<BankAccountDto> account = accountProxy.getMyAccount(email);
		BankAccountDto accountDto = account.getBody();
		
		ResponseEntity<CryptoWalletDto> wallet = walletProxy.getMyWallet(email);
		CryptoWalletDto walletDto = wallet.getBody();
		
		if (accountDto == null) {
		    throw new BankAccountNotFoundException(String.format("Bank account doesn't exist for user with email: %s", email));
		}
		
		if (walletDto == null) {
		    throw new WalletNotFoundException(String.format("Crypto wallet doesn't exist for user with email: %s", email));
		}
		
		if (!accountDto.hasEnoughBalance(from, quantity)) {
		    throw new NotEnoughFundsException(String.format("Not enough funds in account to exchange %s to %s: ", from, to));
		}
		
		BigDecimal bridgeQuantity = quantity;
	    String bridgeCurrency = from;

	    if (!from.equals("EUR") && !from.equals("USD")) {
	    	ResponseEntity<CurrencyConversionDto> bridgeResult = fiatConversionProxy.getConversion(from, "EUR", quantity, "true");
			CurrencyConversionDto bridgeResponse = bridgeResult.getBody();
	        bridgeQuantity = bridgeResponse.getConversionResult().getConvertedAmount();
	        bridgeCurrency = "EUR";
	    }
	    
	    TradeCurrencyModel rate = repo.findByFromAndTo(bridgeCurrency, to);
	    if (rate == null) {
	        throw new IllegalArgumentException("No trade rate for " + bridgeCurrency + " → " + to);
	    }
	    
	    BigDecimal cryptoAmount = bridgeQuantity.multiply(rate.getTradeRate());
	    
	    if (cryptoAmount.compareTo(BigDecimal.ZERO) == 0)
		{
			throw new InvalidConversionResultException(String.format("Conversion from %s to %s resulted in 0. Transaction not allowed.", from, to));		
		}
		

	    accountDto.subtractBalance(from, quantity);
		walletDto.addBalance(to, cryptoAmount);

		accountProxy.updateAccount(accountDto);
	    walletProxy.updateWallet(walletDto);
	    
	    
	    walletDto.setMessage(String.format("Successfully exchanged %s %s for %s %s.", quantity, from, cryptoAmount, to));

	    return ResponseEntity.ok(walletDto);
	}



	/*@Override
	public ResponseEntity<?> getTrade(String email, String from, String to, BigDecimal quantity) {
		if (quantity.compareTo(BigDecimal.valueOf(300,0)) > 0) {
	        throw new InvalidQuantityException(
	            String.format("Quantity of %s is too large", quantity));
	    }

	    // Pretpostavimo da ovde ne koristiš email (ili uzmeš neki default/test user)
	    String testEmail = "test@trade.com";

	    if (fiatProxy.getAllCurrencies().getBody().contains(from)) {
	        return tradeFiatToCrypto(testEmail, from, to, quantity);
	    }

	    if (cryptoProxy.getAllCurrencies().getBody().contains(from)) {
	        return tradeCryptoToFiat(testEmail, from, to, quantity);
	    }

	    return ResponseEntity.badRequest().body(
	        String.format("Unsupported conversion: %s → %s. Only FIAT ↔ CRYPTO allowed.", from, to)
	    );
	}*/

	

}

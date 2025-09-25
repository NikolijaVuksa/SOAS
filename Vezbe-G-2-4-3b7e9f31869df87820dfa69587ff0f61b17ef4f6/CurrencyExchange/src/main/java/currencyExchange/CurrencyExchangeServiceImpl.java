package currencyExchange;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import api.services.CurrencyExchangeService;
import dto.CurrencyExchangeDto;
import util.exceptions.CurrencyDoesntExistException;
import util.exceptions.NoDataFoundException;

@RestController
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService{

	@Autowired
	private CurrencyExchangeRepository repo;
	
	@Autowired
	private Environment environment;
	
	@Override
	public ResponseEntity<?> getCurrencyExchange(String from, String to) {
		String missingCurrency = null;
		List<String> validCurrencies = repo.findAllDistinctCurrencies();
		//proveriti dali from parametar odgovara nekoj valuti
		if(!isValidCurrency(from)) missingCurrency = from;
		//proveriti da li to parametar odgovara nekoj valuti
		else if(!isValidCurrency(to)) missingCurrency = to;
		//provera da li je missingcurrency != null i ako jeste bacanje domain exception-a
		if(missingCurrency != null) throw new CurrencyDoesntExistException(String.format("Currency %s doesn't exist in the database", missingCurrency), validCurrencies);

		
		CurrencyExchangeModel dbResponse  = repo.findByFromAndTo(from, to);
		
		if(dbResponse == null) 
		{
			throw new NoDataFoundException(String.format("Requested exchange rate from: [%s to %s] does not exist", from,to), validCurrencies);
		}//vratiti  domain exception
		CurrencyExchangeDto dto = new CurrencyExchangeDto(dbResponse.getFrom(), dbResponse.getTo(), dbResponse.getExchangeRate());
		dto.setPort(environment.getProperty("local.server.port"));
		return ResponseEntity.ok(dto);
	}

	public boolean isValidCurrency(String currency) {
		List<String> currencies = repo.findAllDistinctCurrencies();
		for(String s: currencies) {
			if(s.equalsIgnoreCase(currency)) {
				return true;
			}
		}
		return false;
	}
	
	public ResponseEntity<List<String>> getAllCurrencies() {
	    return ResponseEntity.ok(repo.findAllDistinctCurrencies());
	}
}

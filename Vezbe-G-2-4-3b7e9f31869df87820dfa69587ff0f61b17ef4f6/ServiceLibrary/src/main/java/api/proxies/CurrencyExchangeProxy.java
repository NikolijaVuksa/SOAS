package api.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dto.CurrencyExchangeDto;

@FeignClient("currency-exchange")
public interface CurrencyExchangeProxy {
	
	@GetMapping("/currency-exchange")
	ResponseEntity<CurrencyExchangeDto> getExchangeFeign(@RequestParam (value = "from") String from, 
			@RequestParam (value = "to")String to);

	@GetMapping("/currency-exchange/currencies")
	public ResponseEntity<List<String>> getAllCurrencies();
}

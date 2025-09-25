package api.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dto.CryptoExchangeDto;

@FeignClient("crypto-exchange")
public interface CryptoExchangeProxy {
	
	@GetMapping("/crypto-exchange")
	ResponseEntity<CryptoExchangeDto> getCryptoExchangeFeign(@RequestParam (value = "from") String from, 
			@RequestParam (value = "to")String to);

	@GetMapping("/crypto-exchange/currencies")
	public ResponseEntity<List<String>> getAllCurrencies();
}


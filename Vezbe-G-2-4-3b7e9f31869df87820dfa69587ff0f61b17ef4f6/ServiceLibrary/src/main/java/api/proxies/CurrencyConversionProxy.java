package api.proxies;

import java.math.BigDecimal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import dto.CurrencyConversionDto;

@FeignClient("currency-conversion")
public interface CurrencyConversionProxy {
	
	@GetMapping("/currency-conversion-feign")
	ResponseEntity<?> getConversionFeign(@RequestHeader("X-User-Email") String email, 
			@RequestParam(value="from") String from, 
			@RequestParam(value="to") String to, 
			@RequestParam(value="quantity") BigDecimal quantity,
			@RequestHeader(value = "X-Internal-Call", defaultValue = "false") String internalCall);
	
	@GetMapping("/currency-conversion")
	ResponseEntity<CurrencyConversionDto> getConversion(@RequestParam(value="to") String from, 
			@RequestParam(value="to") String to, 
			@RequestParam(value="quantity") BigDecimal quantity,
			@RequestHeader(value = "X-Internal-Call", defaultValue = "false") String internalCall);
	
}

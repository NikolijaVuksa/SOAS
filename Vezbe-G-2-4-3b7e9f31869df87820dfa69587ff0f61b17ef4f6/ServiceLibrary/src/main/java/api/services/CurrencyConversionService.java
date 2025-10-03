package api.services;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import dto.CurrencyConversionDto;

@Service
public interface CurrencyConversionService {

	@GetMapping("/currency-conversion")
	ResponseEntity<CurrencyConversionDto> getConversion(@RequestParam String from, 
			@RequestParam String to, 
			@RequestParam BigDecimal quantity,
			@RequestHeader(value = "X-Internal-Call", defaultValue = "false") String internalCall);
	
	@GetMapping("/currency-conversion-feign")
	ResponseEntity<?> getConversionFeign(@RequestHeader("X-User-Email") String email, 
			@RequestParam String from, 
			@RequestParam String to, 
			@RequestParam BigDecimal quantity,
			@RequestHeader(value = "X-Internal-Call", defaultValue = "false") String internalCall);
	
	
}

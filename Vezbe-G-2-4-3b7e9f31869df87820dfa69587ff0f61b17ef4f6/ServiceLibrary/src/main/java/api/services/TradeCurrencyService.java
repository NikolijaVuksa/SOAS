package api.services;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public interface TradeCurrencyService {

	/*@GetMapping("/trade-currency")
	ResponseEntity<?> getTrade(@RequestHeader("X-User-Email") String email, @RequestParam String from, @RequestParam String to, @RequestParam BigDecimal quantity);*/
	
	@GetMapping("/trade-currency")
	ResponseEntity<?> getTradeFeign(@RequestHeader("X-User-Email") String email, @RequestParam String from, @RequestParam String to, @RequestParam BigDecimal quantity);
	
}

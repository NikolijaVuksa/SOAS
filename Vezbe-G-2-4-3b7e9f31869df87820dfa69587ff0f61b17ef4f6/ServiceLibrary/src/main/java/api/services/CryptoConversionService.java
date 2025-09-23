package api.services;

import java.math.BigDecimal;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import dto.CryptoWalletDto;

@Service
public interface CryptoConversionService {
	@GetMapping("/crypto-conversion")
	ResponseEntity<?> getCryptoConversion(@RequestHeader("X-User-Email") String email, @RequestParam String from, @RequestParam String to, @RequestParam BigDecimal quantity);
	
	@GetMapping("/crypto-conversion-feign")
	ResponseEntity<CryptoWalletDto> getCryptoConversionFeign(@RequestHeader("X-User-Email") String email, @RequestParam String from, @RequestParam String to, @RequestParam BigDecimal quantity);


}



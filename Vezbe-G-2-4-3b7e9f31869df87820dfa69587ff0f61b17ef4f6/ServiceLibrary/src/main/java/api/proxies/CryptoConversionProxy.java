package api.proxies;

import java.math.BigDecimal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import dto.CryptoWalletDto;

@FeignClient("crypto-conversion")
public interface CryptoConversionProxy {

	@GetMapping("/crypto-conversion-feign")
	ResponseEntity<CryptoWalletDto> getCryptoConversionFeign(@RequestHeader("X-User-Email") String email, @RequestParam String from, @RequestParam String to, @RequestParam BigDecimal quantity);

}

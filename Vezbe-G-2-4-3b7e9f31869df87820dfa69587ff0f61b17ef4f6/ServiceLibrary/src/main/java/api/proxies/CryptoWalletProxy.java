package api.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import dto.CryptoWalletDto;

@FeignClient(name = "crypto-wallet")
public interface CryptoWalletProxy {

	@PostMapping("/wallets/newWallet")
	ResponseEntity<?> createWallet(@RequestBody CryptoWalletDto dto);

    @DeleteMapping("/wallets")
    void deleteWallet(@RequestBody CryptoWalletDto dto);
    
    @GetMapping("/wallets/myWallet")
    ResponseEntity<CryptoWalletDto> getMyWallet(@RequestHeader("X-User-Email") String email);
    
    @PutMapping("/wallets")
    ResponseEntity<?> updateWallet(@RequestBody CryptoWalletDto dto);
}

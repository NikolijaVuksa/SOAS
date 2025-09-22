package api.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import dto.CryptoWalletDto;

@FeignClient(name = "crypto-wallet")
public interface CryptoWalletProxy {

	@PostMapping("/wallets/newWallet")
	ResponseEntity<?> createWallet(@RequestBody CryptoWalletDto dto);


    @DeleteMapping("/wallets")
    void deleteWallet(@RequestBody CryptoWalletDto dto);
}

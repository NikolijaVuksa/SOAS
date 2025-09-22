package api.services;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import dto.CryptoWalletDto;
import java.util.List;

@Service
public interface CryptoWalletService {

	@GetMapping("/wallets")
    List<CryptoWalletDto> getAllWallets();

    @GetMapping("/wallets/myWallet")
    ResponseEntity<?> getMyWallet(@RequestHeader("X-User-Email") String email);

    @PostMapping("/wallets/newWallet")
    ResponseEntity<?> createWallet(@RequestBody CryptoWalletDto dto);

    @PutMapping("/wallets")
    ResponseEntity<?> updateWallet(@RequestBody CryptoWalletDto dto);

    @DeleteMapping("/wallets")
    ResponseEntity<?> deleteWallet(@RequestBody CryptoWalletDto dto);
}


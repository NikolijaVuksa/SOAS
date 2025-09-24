package api.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import dto.BankAccountDto;

@FeignClient("bank-account-service")
public interface BankAccountProxy {

    @PostMapping("/accounts/newAccount")
    void createAccount(@RequestBody BankAccountDto dto);

    @DeleteMapping("/accounts")
    void deleteAccount(@RequestBody BankAccountDto dto);
    
    @GetMapping("/accounts/myAccount")
    ResponseEntity<BankAccountDto> getMyAccount(@RequestHeader("X-User-Email") String email);
    
    @PutMapping("/accounts")
    ResponseEntity<?> updateAccount(@RequestBody BankAccountDto dto);
}


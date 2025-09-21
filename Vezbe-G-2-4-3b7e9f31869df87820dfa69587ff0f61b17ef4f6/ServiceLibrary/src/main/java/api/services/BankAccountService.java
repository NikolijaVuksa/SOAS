package api.services;

import java.util.List;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import api.dtos.BankAccountDto;

public interface BankAccountService {

    @GetMapping("/accounts")
    List<BankAccountDto> getAllAccounts();

    @GetMapping("/accounts/myAccount")
    ResponseEntity<?> getMyAccount(@RequestHeader("X-User-Email") String email);

    @PostMapping("/accounts/newAccount")
    ResponseEntity<?> createAccount(@RequestBody BankAccountDto dto);

    @PutMapping("/accounts")
    ResponseEntity<?> updateAccount(@RequestBody BankAccountDto dto);

    @DeleteMapping("/accounts")
    ResponseEntity<?> deleteAccount(@RequestBody BankAccountDto dto);

}


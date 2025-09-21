package api.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import api.dtos.BankAccountDto;

@FeignClient("bank-account-service")
public interface BankAccountProxy {

    @PostMapping("/accounts/newAccount")
    void createAccount(@RequestBody BankAccountDto dto);

    @DeleteMapping("/accounts")
    void deleteAccount(@RequestBody BankAccountDto dto);
}


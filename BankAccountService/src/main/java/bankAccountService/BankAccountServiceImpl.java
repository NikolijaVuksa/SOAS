package bankAccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import api.dtos.BankAccountDto;
import api.dtos.UserDto;
import api.proxies.UsersServiceProxy;
import api.services.BankAccountService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private BankAccountRepository repo;

    @Autowired
    private UsersServiceProxy usersProxy;

    @Override
    public List<BankAccountDto> getAllAccounts() {
    	List<BankAccountModel> models = repo.findAll();
		List<BankAccountDto> dtos = new ArrayList<BankAccountDto>();
		for(BankAccountModel model: models) {
			dtos.add(convertModelToDto(model));
		}
		return dtos;
    }

    /*@Override
    public ResponseEntity<?> getMyAccount(String email) {
        BankAccountModel account = repo.findByEmail(email);
        return account != null ? convertModelToDto(account) : null;
    }*/
    
    @Override
    public ResponseEntity<?> getMyAccount(String email) {
        BankAccountModel account = repo.findByEmail(email);

        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("No account found for user: " + email);
        }

        BankAccountDto dto = convertModelToDto(account);
        return ResponseEntity.ok(dto);
    }


    /*@Override
    public ResponseEntity<?> createAccount(String email) {
        UserDto user = usersProxy.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        if (!"USER".equals(user.getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only USER role can have bank account");
        }
        if (repo.findByEmail(email) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Account already exists");
        }

        BankAccountModel account = new BankAccountModel(email);
        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(account));
    }*/
    
    @Override
    public ResponseEntity<?> createAccount(BankAccountDto dto) {
        String email = dto.getEmail();

        UserDto user = usersProxy.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Cannot create account: user does not exist");
        }

        if (!"USER".equals(user.getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                 .body("Only USER role can have bank account");
        }

        if (repo.findByEmail(email) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                 .body("Account already exists");
        }

        BankAccountModel account = new BankAccountModel(email,dto.getEur(),dto.getUsd(),dto.getChf(),dto.getGbp(),dto.getCad(),dto.getRsd());
        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(account));
    }

    @Override
    public ResponseEntity<?> updateAccount(BankAccountDto dto) {
        BankAccountModel account = repo.findByEmail(dto.getEmail());
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }
        account.setEur(dto.getEur());
        account.setUsd(dto.getUsd());
        account.setRsd(dto.getRsd());
        account.setChf(dto.getChf());
        account.setCad(dto.getCad());
        account.setGbp(dto.getGbp());
        return ResponseEntity.ok(repo.save(account));
    }

    @Override
    public ResponseEntity<?> deleteAccount(BankAccountDto dto) {
        String email = dto.getEmail();
        BankAccountModel account = repo.findByEmail(email);

        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }

        repo.delete(account);
        return ResponseEntity.ok("Account deleted");
    }


    private BankAccountDto convertModelToDto(BankAccountModel model) {
        BankAccountDto dto = new BankAccountDto(model.getEmail());
        dto.setEur(model.getEur());
        dto.setUsd(model.getUsd());
        dto.setRsd(model.getRsd());
        dto.setChf(model.getChf());
        dto.setCad(model.getCad());
        dto.setGbp(model.getGbp());
        return dto;
    }
}

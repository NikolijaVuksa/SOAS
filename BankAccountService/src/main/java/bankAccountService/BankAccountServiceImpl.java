package bankAccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import api.proxies.UsersServiceProxy;
import api.services.BankAccountService;
import dto.BankAccountDto;
import dto.CryptoWalletDto;
import dto.UserDto;
import util.exceptions.BankAccountNotFoundException;
import util.exceptions.WalletNotFoundException;

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
    
    @Override
    public ResponseEntity<BankAccountDto> getMyAccount(String email) {
        BankAccountModel account = repo.findByEmail(email);


        if (account == null) {
            return null;
        }

        BankAccountDto dto = convertModelToDto(account);
        return ResponseEntity.ok(dto);
    }

    
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

        BankAccountModel account = new BankAccountModel(email,dto.getEUR(),dto.getUSD(),dto.getCHF(),dto.getGBP(),dto.getCAD(),dto.getRSD());
        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(account));
    }

    @Override
    public ResponseEntity<?> updateAccount(BankAccountDto dto) {
        BankAccountModel account = repo.findByEmail(dto.getEmail());
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }
        account.setEUR(dto.getEUR());
        account.setUSD(dto.getUSD());
        account.setRSD(dto.getRSD());
        account.setCHF(dto.getCHF());
        account.setCAD(dto.getCAD());
        account.setGBP(dto.getGBP());
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
        dto.setEUR(model.getEUR());
        dto.setUSD(model.getUSD());
        dto.setRSD(model.getRSD());
        dto.setCHF(model.getCHF());
        dto.setCAD(model.getCAD());
        dto.setGBP(model.getGBP());
        return dto;
    }
}

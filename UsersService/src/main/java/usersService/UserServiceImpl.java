package usersService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import api.proxies.BankAccountProxy;
import api.proxies.CryptoWalletProxy;
import api.proxies.CurrencyExchangeProxy;
import api.services.UsersService;
import dto.BankAccountDto;
import dto.CryptoWalletDto;
import dto.UserDto;

@RestController
public class UserServiceImpl implements UsersService{

	@Autowired
	private UserRepository repo;
	
	@Autowired
	private BankAccountProxy bankAccountProxy;
	
	@Autowired
	private CryptoWalletProxy cryptoWalletProxy;
	
	@Override
	public List<UserDto> getUsers() {
		List<UserModel> models = repo.findAll();
		List<UserDto> dtos = new ArrayList<UserDto>();
		for(UserModel model: models) {
			dtos.add(convertModelToDto(model));
		}
		return dtos;
	}

	@Override
	public UserDto getUserByEmail(String email) {
	    UserModel model = repo.findByEmail(email);
	    if (model == null) {
	        return null; 
	    }
	    return convertModelToDto(model);
	}
	
	@Override
	public ResponseEntity<?> createUser(UserDto dto) {
	    if (repo.findByEmail(dto.getEmail()) != null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("User with passed email already exist");
	    }
	    
	    
	    if ("USER".equalsIgnoreCase(dto.getRole())) {
	        UserModel model = convertDtoToModel(dto);
	        repo.save(model);

	        bankAccountProxy.createAccount(new BankAccountDto(dto.getEmail()));
	        cryptoWalletProxy.createWallet(new CryptoWalletDto(dto.getEmail()));

	        return ResponseEntity.status(HttpStatus.CREATED)
                    .body("User created, bank account and crypto wallet opened");
	    }

	    if ("ADMIN".equalsIgnoreCase(dto.getRole())) {
	        UserModel model = convertDtoToModel(dto);
	        repo.save(model);
	        return ResponseEntity.status(HttpStatus.CREATED)
	                .body("Admin created");
	    }

	    if ("OWNER".equalsIgnoreCase(dto.getRole())) {
	        if (repo.findByRole("OWNER") != null) {
		        return ResponseEntity.status(HttpStatus.CONFLICT).body("There can only be one OWNER");

	        }
	        UserModel model = convertDtoToModel(dto);
	        repo.save(model);
	        return ResponseEntity.status(HttpStatus.CREATED)
	                .body("Owner created");
	    }

	    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	            .body("Unknown role: " + dto.getRole());
	}




	/*@Override
	public ResponseEntity<?> createAdmin(UserDto dto) {
		if(repo.findByEmail(dto.getEmail()) == null) {
			dto.setRole("ADMIN");
			UserModel model= convertDtoToModel(dto);
			return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(model));
		}else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Admin with passed email already exist");
			
		}
	}

	@Override
	public ResponseEntity<?> createUser(UserDto dto) {
	    if (repo.findByEmail(dto.getEmail()) == null) {
	        dto.setRole("USER");
	        UserModel model = convertDtoToModel(dto);
	        repo.save(model);

	        BankAccountDto accountDto = new BankAccountDto(dto.getEmail());
	        bankAccountProxy.createAccount(accountDto);

	        CryptoWalletDto walletDto = new CryptoWalletDto(dto.getEmail());
	        cryptoWalletProxy.createWallet(walletDto);
	        return ResponseEntity.status(HttpStatus.CREATED)
	                             .body("User created, bank account and crypto wallet opened");
	    } else {
	        return ResponseEntity.status(HttpStatus.CONFLICT)
	                             .body("User with passed email already exists");
	    }
	}
		@Override
	public ResponseEntity<?> createOwner(UserDto dto) {
		if(repo.findByRole("OWNER") != null) {
		        return ResponseEntity.status(HttpStatus.CONFLICT).body("There can only be one OWNER");
		    }
	    dto.setRole("OWNER");
	    UserModel model = convertDtoToModel(dto);
	    return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(model));
	}
	*/


	@Override
	public ResponseEntity<?> updateUser(UserDto dto) {
		if(repo.findByEmail(dto.getEmail()) != null) {
			
			repo.updateUser(dto.getEmail(), dto.getPassword(), dto.getRole());
			return ResponseEntity.status(HttpStatus.OK).body(dto);
		}
		else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("User with passed email doesn't exist exist");	
		}
	}
	
	@Override
	public ResponseEntity<?> deleteUser(String email) {
	    UserModel existing = repo.findByEmail(email);
	    if (existing != null) {
	        repo.delete(existing);

	        BankAccountDto dto = new BankAccountDto(email);
	        bankAccountProxy.deleteAccount(dto);
	        
	        CryptoWalletDto walletDto = new CryptoWalletDto(dto.getEmail());
	        cryptoWalletProxy.deleteWallet(walletDto);

	        return ResponseEntity.ok("User, bank account and crypto wallet deleted");
	    } 
	    else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
	    }
	}

	
	public UserDto convertModelToDto(UserModel model)
	{
		return new UserDto(model.getEmail(),model.getPassword(),model.getRole());
		
	}
	public UserModel convertDtoToModel(UserDto dto)
	{
		return new UserModel(dto.getEmail(),dto.getPassword(),dto.getRole());
		
	}
	
}
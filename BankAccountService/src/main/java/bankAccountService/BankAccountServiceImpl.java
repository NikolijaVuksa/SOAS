package bankAccountService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import api.dtos.BankAccountDto;
import api.services.BankAccountService;

@RestController
public class BankAccountServiceImpl implements BankAccountService {

	@Autowired
	private BankAccountRepository repo;
	
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
	public BankAccountDto getMyAccount(String email) {
		return convertModelToDto(repo.findByEmail(email));
	}

	@Override
	public ResponseEntity<?> createAccount(BankAccountDto dto) {
		return null;
	}

	@Override
	public ResponseEntity<?> updateAccount(BankAccountDto dto) {
		return null;
	}

	@Override
	public ResponseEntity<?> deleteAccount(String email) {
		return null;
	}
	
	public BankAccountDto convertModelToDto(BankAccountModel model)
	{
		return new BankAccountDto(model.getEmail(),model.getEur(),model.getUsd(),model.getChf(),model.getGbp(),model.getCad(),model.getRsd());
		
	}
	public BankAccountModel convertDtoToModel(BankAccountDto dto)
	{
		return new BankAccountModel(dto.getEmail(),dto.getEur(),dto.getUsd(),dto.getChf(),dto.getGbp(),dto.getCad(),dto.getRsd());
		
	}

}
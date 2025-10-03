package cryptoWallet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import api.proxies.UsersServiceProxy;
import api.services.CryptoWalletService;

import dto.CryptoWalletDto;
import dto.UserDto;

@RestController
public class CryptoWalletServiceImpl implements CryptoWalletService {

    @Autowired
    private CryptoWalletRepository repo;

    @Autowired
    private UsersServiceProxy usersProxy;

    @Override
    public List<CryptoWalletDto> getAllWallets() {
    	List<CryptoWallet> models = repo.findAll();
		List<CryptoWalletDto> dtos = new ArrayList<CryptoWalletDto>();
		for(CryptoWallet model: models) {
			dtos.add(convertModelToDto(model));
		}
		return dtos;
    }

    
    @Override
    public ResponseEntity<CryptoWalletDto> getMyWallet(String email) {
    	CryptoWallet wallet = repo.findByEmail(email);

        if (wallet == null) {
        	return null;
        }

        CryptoWalletDto dto = convertModelToDto(wallet);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<?> createWallet(CryptoWalletDto dto) {
        String email = dto.getEmail();

        UserDto user = usersProxy.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Cannot create crypto wallet: user does not exist");
        }

        if (!"USER".equals(user.getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                 .body("Only USER role can have crypto wallet");
        }

        if (repo.findByEmail(email) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                 .body("Crypto wallet already exists");
        }

        CryptoWallet wallet = new CryptoWallet(email,dto.getBTC(),dto.getETH(),dto.getUST());
        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(wallet));
    }

    @Override
    public ResponseEntity<?> updateWallet(CryptoWalletDto dto) {
    	CryptoWallet wallet = repo.findByEmail(dto.getEmail());
        if (wallet == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Crypto wallet not found");
        }
        wallet.setBTC(dto.getBTC());
        wallet.setETH(dto.getETH());
        wallet.setUST(dto.getUST());
        return ResponseEntity.ok(repo.save(wallet));
    }

    @Override
    public ResponseEntity<?> deleteWallet(CryptoWalletDto dto) {
        String email = dto.getEmail();
        CryptoWallet wallet = repo.findByEmail(email);

        if (wallet == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Crypto wallet not found");
        }

        repo.delete(wallet);
        return ResponseEntity.ok("Crypto wallet deleted");
    }


    private CryptoWalletDto convertModelToDto(CryptoWallet model) {
    	CryptoWalletDto dto = new CryptoWalletDto(model.getEmail());
        dto.setBTC(model.getBTC());
        dto.setETH(model.getETH());
        dto.setUST(model.getUST());
        return dto;
    }
}

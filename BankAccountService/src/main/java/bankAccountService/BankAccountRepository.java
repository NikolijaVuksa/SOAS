package bankAccountService;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;


public interface BankAccountRepository extends JpaRepository<BankAccountModel, Integer> {
	
	  BankAccountModel findByEmail(String email);
	  boolean existsByEmail(String email);
	  void deleteByEmail(String email);
}

	

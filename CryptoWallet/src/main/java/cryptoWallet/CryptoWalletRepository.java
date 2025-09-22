package cryptoWallet;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CryptoWalletRepository extends JpaRepository<CryptoWallet, Integer> {
	CryptoWallet findByEmail(String email);
    void deleteByEmail(String email);
}

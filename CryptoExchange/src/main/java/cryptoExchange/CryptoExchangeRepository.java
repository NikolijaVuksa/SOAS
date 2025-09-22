package cryptoExchange;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CryptoExchangeRepository extends JpaRepository<CryptoExchangeModel, Integer> {

  CryptoExchangeModel findByFromAndTo(String from, String to);

  @Query(value = """
      SELECT DISTINCT crypto_from AS crypto from crypto_exchange
  	  UNION
  	  SELECT DISTINCT crypto_to AS crypto from crypto_exchange
      """, nativeQuery = true)
  
  List<String> findAllDistinctCryptos();
}


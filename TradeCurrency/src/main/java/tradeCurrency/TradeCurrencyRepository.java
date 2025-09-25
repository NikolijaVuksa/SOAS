package tradeCurrency;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface TradeCurrencyRepository extends JpaRepository<TradeCurrencyModel, Integer>{
	
	TradeCurrencyModel findByFromAndTo(String from, String to);
	
	@Query(value = """ 
			SELECT DISTINCT trade_from AS currency from trade_currency
			UNION
			SELECT DISTINCT trade_to AS currency from trade_currency
			""", nativeQuery = true
			)
	List<String> findAllDistinctCurrencies();
}


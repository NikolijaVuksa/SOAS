package currencyConversion;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@ComponentScan(basePackages = {"util.exceptions", "currencyConversion"})
public class CurrencyConversionApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyConversionApplication.class, args);
	}

}

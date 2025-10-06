package apiGateway.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.client.WebClient;

import dto.UserDto;

@Configuration
@EnableWebFluxSecurity
public class ApiGatewayAuthentication {
	
	@Bean
	SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
		http
		.csrf(csrf -> csrf.disable())
		.authorizeExchange(exchange -> exchange
				.pathMatchers(HttpMethod.POST, "/users/**").hasAnyRole("ADMIN", "OWNER") 
	            .pathMatchers(HttpMethod.PUT, "/users/**").hasAnyRole("ADMIN", "OWNER") 
	            .pathMatchers(HttpMethod.DELETE, "/users/**").hasRole("OWNER")           
	            .pathMatchers("/users/**").hasAnyRole("ADMIN", "OWNER")                  

	            .pathMatchers("/currency-exchange/**").permitAll()

	            .pathMatchers("/currency-conversion/**").hasRole("USER")

	            .pathMatchers(HttpMethod.GET, "/accounts/myAccount").hasRole("USER")   
	            .pathMatchers("/accounts/**").hasRole("ADMIN") 
	            .pathMatchers(HttpMethod.POST, "/accounts/newAccount").hasRole("ADMIN")
	            
	            .pathMatchers("/crypto-exchange/**").permitAll()
	            
	            .pathMatchers(HttpMethod.GET, "/wallets/myWallet").hasRole("USER")   
	            .pathMatchers("/wallets/**").hasRole("ADMIN") 
	            .pathMatchers(HttpMethod.POST, "/wallets/newWallet").hasRole("ADMIN")
	            
	            .pathMatchers("/crypto-conversion/**").hasRole("USER")

	            .pathMatchers("/trade-currency/**").hasRole("USER")


				).httpBasic(Customizer.withDefaults());
		
		return http.build();
	}
	
	@Bean
	ReactiveUserDetailsService reactiveUserDetailsService(WebClient.Builder webClientBuilder, BCryptPasswordEncoder encoder) {
		WebClient client = webClientBuilder.baseUrl("http://localhost:8770").build();
		//WebClient client = webClientBuilder.baseUrl("http://users-service:8770").build();
		
		return user -> client.get()
				.uri(uriBuilder -> uriBuilder
						.path("/users/email")
						.queryParam("email", user)
						.build()
				)
				.retrieve()
				.bodyToMono(UserDto.class)
				.map(dto -> User.withUsername(dto.getEmail())
						.password(encoder.encode(dto.getPassword()))
						.roles(dto.getRole())
						.build()
				);
			
		}
	
	
	
	@Bean
	BCryptPasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}

}
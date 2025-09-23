package apiGateway.authentication;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import reactor.core.publisher.Mono;

@Configuration
public class UserEmailHeaderForAccount {

	@Bean
	public GlobalFilter addUserEmailHeader() {
	    return (exchange, chain) -> ReactiveSecurityContextHolder.getContext()
	        .flatMap(securityContext -> {
	            Authentication auth = securityContext.getAuthentication();
	            if (auth != null && auth.getPrincipal() instanceof User userDetails) {
	                String email = userDetails.getUsername(); 
	                var mutatedExchange = exchange.mutate()
	                    .request(r -> r.headers(h -> h.set("X-User-Email", email)))
	                    .build();
	                return chain.filter(mutatedExchange);
	            }
	            return chain.filter(exchange);
	        });
	}

}

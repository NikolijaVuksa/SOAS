package api.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dto.UserDto;

@FeignClient("users-service")
public interface UsersServiceProxy {

	@GetMapping("/users/email")
	UserDto getUserByEmail(@RequestParam(value="email") String email);
}

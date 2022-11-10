package hr.fer.dsd.privtap.rest;

import hr.fer.dsd.privtap.model.user.UserRequest;
import hr.fer.dsd.privtap.model.user.UserResponse;
import hr.fer.dsd.privtap.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/")
public class UserController {

    private final UserService service;

    @GetMapping("/")
    public String home() {
        return "Home page!";
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess(OAuth2AuthenticationToken authentication) {
        var response = authentication.getPrincipal().getAttributes();
        service.registerUser(new UserRequest(response.get("name").toString(), response.get("email").toString()));
        return "loginSuccess" + response.toString();
    }

    @GetMapping("/loginFailure")
    public String loginFailure() {
        return "loginFailure";
    }

    @GetMapping("/{userId}")
    public UserResponse getById(@PathVariable @NotNull String userId) {
        return service.getById(userId)
                .orElse(null);
    }

    @PostMapping("/")
    public UserResponse update(@RequestBody @NotNull UserRequest request) {
        return service.update(request);
    }

    @GetMapping("/all")
    public List<UserResponse> fetchAllUsers(){
        return service.getAllUsers();
    }


}

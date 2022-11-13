package hr.fer.dsd.privtap.rest;

import hr.fer.dsd.privtap.model.automation.AutomationRequest;
import hr.fer.dsd.privtap.model.user.User;
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
        service.registerUser(new User(null, response.get("name").toString(), response.get("email").toString(), null));
        return "loginSuccess";
    }

    @GetMapping("/loginFailure")
    public String loginFailure() {
        return "loginFailure";
    }

    @GetMapping("/{userId}")
    public User getById(@PathVariable @NotNull String userId) {
        return service.getById(userId);
    }

    @PatchMapping("/")
    public User update(@RequestBody @NotNull User user) {
        return service.update(user);
    }

    @GetMapping("/all")
    public List<User> fetchAllUsers() {
        return service.getAllUsers();
    }

    @PostMapping("/automation/{userId}")
    public User registerAutomation(@PathVariable @NotNull String userId, @RequestBody AutomationRequest request) {
        return service.registerAutomation(userId, request);
    }

}

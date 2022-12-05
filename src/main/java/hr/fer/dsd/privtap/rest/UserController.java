package hr.fer.dsd.privtap.rest;

import hr.fer.dsd.privtap.model.automation.Automation;
import hr.fer.dsd.privtap.model.automation.AutomationRequest;
import hr.fer.dsd.privtap.model.user.User;
import hr.fer.dsd.privtap.security.CurrentUser;
import hr.fer.dsd.privtap.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/")
public class UserController {

    private final UserService service;

    @GetMapping("/")
    public String home() {
        return "Home page!";
    }

    @GetMapping("/user/{userId}")
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

    @DeleteMapping("/automation/{userId}")
    public void deleteAutomation(@PathVariable @NotNull String userId, @RequestBody Automation automation) {
        service.deleteAutomation(userId, automation);
    }

    @GetMapping("/automation/{userId}")
    public Set<Automation> getAllAutomations(@PathVariable @NotNull String userId) {
        return service.getAllAutomations(userId);
    }
}

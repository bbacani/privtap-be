package hr.fer.dsd.privtap.rest;

import hr.fer.dsd.privtap.domain.entities.UserEntity;
import hr.fer.dsd.privtap.domain.repositories.UserRepository;
import hr.fer.dsd.privtap.exception.ResourceNotFoundException;
import hr.fer.dsd.privtap.model.automation.Automation;
import hr.fer.dsd.privtap.model.automation.AutomationRequest;
import hr.fer.dsd.privtap.model.user.User;
import hr.fer.dsd.privtap.security.CurrentUser;
import hr.fer.dsd.privtap.service.EndUserService;
import lombok.AllArgsConstructor;
import hr.fer.dsd.privtap.security.UserPrincipal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/")
public class UserController {

    private final UserRepository userRepository;

    private final EndUserService service;

    @GetMapping("/")
    public String home() {
        return "Home page!";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public UserEntity getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

    @GetMapping("/user/{userId}")
    public User getById(@PathVariable @NotNull String userId) {
        return service.getById(userId);
    }

    @GetMapping("/all")
    public List<User> fetchAllUsers() {
        return service.getAllUsers();
    }

    @PreAuthorize("hasRole('USER') and principal.getId().equals(#userId)")
    @PostMapping("/automation/{userId}")
    public User registerAutomation(@PathVariable @NotNull String userId, @RequestBody AutomationRequest request) {
        return service.registerAutomation(userId, request);
    }

    @PreAuthorize("hasRole('USER') and principal.getId().equals(#userId)")
    @DeleteMapping("/automation/{userId}/{automationId}")
    public void deleteAutomation(@PathVariable @NotNull String userId, @PathVariable @NotNull String automationId) {
        service.deleteAutomation(userId, automationId);
    }
    @PreAuthorize("hasRole('USER') and principal.getId().equals(#userId)")
    @GetMapping("/automation/{userId}")
    public Set<Automation> getAllAutomations(@PathVariable @NotNull String userId) {
        return service.getAllAutomations(userId);
    }
}

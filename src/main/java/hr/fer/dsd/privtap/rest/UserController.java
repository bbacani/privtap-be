package hr.fer.dsd.privtap.rest;

import hr.fer.dsd.privtap.model.user.UserRequest;
import hr.fer.dsd.privtap.model.user.UserResponse;
import hr.fer.dsd.privtap.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService service;


    @GetMapping("/demo")
    public String demo(){
        return "Ovo je s BE!";
    }


    @GetMapping("/{userId}")
    public UserResponse getById(@PathVariable @NotNull String userId) {
        return service.getById(userId)
                .orElse(null);
    }

    @PostMapping("/")
    public UserResponse save(@RequestBody @NotNull UserRequest request) {
        return service.save(request);
    }



}

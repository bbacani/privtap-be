package hr.fer.dsd.privtap.rest;

import hr.fer.dsd.privtap.model.user.UserRequest;
import hr.fer.dsd.privtap.model.user.UserResponse;
import hr.fer.dsd.privtap.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService service;


    @GetMapping("/login")
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

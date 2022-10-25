package hr.fer.dsd.privtap.service;

import hr.fer.dsd.privtap.domain.entities.User;
import hr.fer.dsd.privtap.domain.repositories.UserRepository;
import hr.fer.dsd.privtap.model.user.UserRequest;
import hr.fer.dsd.privtap.model.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;


    public UserResponse save(UserRequest request) {
        var user = request.getId() != null ? update(request) : create(request);
        return convert(user);
    }

    public Optional<UserResponse> getById(Long id) {
        return repository.findById(id).map(this::convert);
    }

    private User update(UserRequest request) {
        var user = findById(request.getId()).get();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return repository.save(user);
    }

    private User create(UserRequest request) {
        return repository.save(
                new User(null, request.getUsername(), request.getEmail(), request.getPassword())
        );
    }

    private Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    private UserResponse convert(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail());
    }

}
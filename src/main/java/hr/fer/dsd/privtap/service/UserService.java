package hr.fer.dsd.privtap.service;

import hr.fer.dsd.privtap.domain.entities.User;
import hr.fer.dsd.privtap.domain.repositories.UserRepository;
import hr.fer.dsd.privtap.model.user.UserRequest;
import hr.fer.dsd.privtap.model.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;

    public void registerUser(UserRequest request) {
        if(findByEmail(request.getEmail()).isEmpty())
            create(request);
    }


    public Optional<UserResponse> getById(String id) {
        return repository.findById(id).map(this::convert);
    }

    public UserResponse update(UserRequest request) {
        var user = findByEmail(request.getEmail()).get();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        repository.save(user);
        return convert(user);
    }

    private User create(UserRequest request) {
        return repository.save(
                new User(null, request.getUsername(), request.getEmail())
        );
    }

    private Optional<User> findById(String id) {
        return repository.findById(id);
    }

    private Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }


    private UserResponse convert(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail());
    }

    public List<UserResponse> getAllUsers() {
        List<UserResponse> response=new ArrayList<UserResponse>();
        for(User u : repository.findAll())
            response.add(convert(u));
        return response;
    }
}
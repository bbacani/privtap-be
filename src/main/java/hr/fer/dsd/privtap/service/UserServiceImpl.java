package hr.fer.dsd.privtap.service;

import hr.fer.dsd.privtap.domain.entities.ServiceProviderEntity;
import hr.fer.dsd.privtap.domain.entities.UserEntity;
import hr.fer.dsd.privtap.domain.repositories.ServiceProviderRepository;
import hr.fer.dsd.privtap.domain.repositories.UserRepository;
import hr.fer.dsd.privtap.exception.ResourceNotFoundException;
import hr.fer.dsd.privtap.model.user.GenericUser;
import hr.fer.dsd.privtap.security.UserPrincipal;
import hr.fer.dsd.privtap.utils.mappers.ServiceProviderMapper;
import hr.fer.dsd.privtap.utils.mappers.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ServiceProviderRepository serviceProviderRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email : " + email)
                );

        return UserPrincipal.create(UserMapper.INSTANCE.fromEntity(user));
    }

    @Override
    public UserDetails loadUserById(String id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        Optional<ServiceProviderEntity> serviceProviderOptional = serviceProviderRepository.findById(id);
        GenericUser genericUser;
        if(userOptional.isPresent()) {
            genericUser = UserMapper.INSTANCE.fromEntity(userOptional.get());
        }
        else if (serviceProviderOptional.isPresent()) {
            genericUser = ServiceProviderMapper.INSTANCE.fromEntity(serviceProviderOptional.get());
        }
        else throw new ResourceNotFoundException("User", "id", id);
        return UserPrincipal.create(genericUser);
    }

}

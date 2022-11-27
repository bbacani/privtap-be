package hr.fer.dsd.privtap.security.oauth2;

import hr.fer.dsd.privtap.domain.entities.UserEntity;
import hr.fer.dsd.privtap.domain.repositories.UserRepository;
import hr.fer.dsd.privtap.model.user.User;
import hr.fer.dsd.privtap.service.UserService;
import hr.fer.dsd.privtap.utils.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository repository;
    private final UserService service;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = new OAuth2UserInfo(oAuth2User.getAttributes());
        if (ObjectUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationException("Email not found from OAuth2 provider");
        }

        Optional<UserEntity> userOptional = repository.findByEmail(oAuth2UserInfo.getEmail());
        if (userOptional.isPresent()) {
            User user = UserMapper.INSTANCE.fromEntity(userOptional.get());
            service.update(user);
        } else {
            User user = generateUser(oAuth2UserRequest, oAuth2UserInfo);
            service.create(user);
        }
        return oAuth2User;
    }

    private User generateUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        User user = new User();
        user.setUsername(oAuth2UserInfo.getName());
        user.setEmail(oAuth2UserInfo.getEmail());
        return user;
    }

}

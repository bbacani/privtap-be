package hr.fer.dsd.privtap.security.oauth2;

import hr.fer.dsd.privtap.domain.entities.ServiceProviderEntity;
import hr.fer.dsd.privtap.domain.repositories.ServiceProviderRepository;
import hr.fer.dsd.privtap.exception.OAuth2AuthenticationProcessingException;
import hr.fer.dsd.privtap.model.user.AuthProvider;
import hr.fer.dsd.privtap.security.UserPrincipal;
import hr.fer.dsd.privtap.security.oauth2.user.OAuth2UserInfo;
import hr.fer.dsd.privtap.security.oauth2.user.OAuth2UserInfoFactory;
import hr.fer.dsd.privtap.utils.mappers.ServiceProviderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2ServiceProviderService extends DefaultOAuth2UserService {

    private final ServiceProviderRepository serviceProviderRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2ServiceProvider(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2ServiceProvider(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        Optional<ServiceProviderEntity> serviceProviderOptional = serviceProviderRepository.findByEmail(oAuth2UserInfo.getEmail());
        ServiceProviderEntity serviceProvider;
        if(serviceProviderOptional.isPresent()) {
            serviceProvider = serviceProviderOptional.get();
            if(!serviceProvider.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        serviceProvider.getProvider() + " account. Please use your " + serviceProvider.getProvider() +
                        " account to login.");
            }
            serviceProvider = updateExistingServiceProvider(serviceProvider, oAuth2UserInfo);
        } else {
            serviceProvider = registerNewServiceProvider(oAuth2UserRequest, oAuth2UserInfo);
        }
        return UserPrincipal.create(ServiceProviderMapper.INSTANCE.fromEntity(serviceProvider), oAuth2User.getAttributes());
    }

    private ServiceProviderEntity registerNewServiceProvider(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo){
        ServiceProviderEntity serviceProvider = new ServiceProviderEntity();
        serviceProvider.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        serviceProvider.setProviderId(oAuth2UserInfo.getId());
        serviceProvider.setUsername(oAuth2UserInfo.getName());
        serviceProvider.setEmail(oAuth2UserInfo.getEmail());
        serviceProvider.setImageUrl(oAuth2UserInfo.getImageUrl());

        return serviceProviderRepository.save(serviceProvider);
    }

    private ServiceProviderEntity updateExistingServiceProvider(ServiceProviderEntity existingSP, OAuth2UserInfo oAuth2UserInfo) {
        existingSP.setUsername(oAuth2UserInfo.getName());
        existingSP.setImageUrl(oAuth2UserInfo.getImageUrl());
        return serviceProviderRepository.save(existingSP);
    }

}


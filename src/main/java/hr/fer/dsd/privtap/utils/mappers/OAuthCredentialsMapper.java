package hr.fer.dsd.privtap.utils.mappers;

import hr.fer.dsd.privtap.domain.entities.OAuthCredentialsEntity;
import hr.fer.dsd.privtap.model.auth0.OAuthCredentials;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface OAuthCredentialsMapper {

    OAuthCredentialsMapper INSTANCE = Mappers.getMapper(OAuthCredentialsMapper.class);

    OAuthCredentials fromEntity(OAuthCredentialsEntity platform);

    OAuthCredentialsEntity toEntity(OAuthCredentials model);

    OAuthCredentialsEntity updateEntity(@MappingTarget OAuthCredentialsEntity entity, OAuthCredentials platform);
}

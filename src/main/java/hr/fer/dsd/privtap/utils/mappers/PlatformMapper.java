package hr.fer.dsd.privtap.utils.mappers;

import hr.fer.dsd.privtap.domain.entities.PlatformEntity;
import hr.fer.dsd.privtap.domain.entities.UserEntity;
import hr.fer.dsd.privtap.model.user.Platform;
import hr.fer.dsd.privtap.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface PlatformMapper {
    PlatformMapper INSTANCE = Mappers.getMapper(PlatformMapper.class);

    Platform fromEntity(PlatformEntity platform);

    PlatformEntity toEntity(Platform model);

    PlatformEntity updateEntity(@MappingTarget PlatformEntity entity, Platform platform);

}

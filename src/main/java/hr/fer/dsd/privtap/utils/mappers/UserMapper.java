package hr.fer.dsd.privtap.utils.mappers;

import hr.fer.dsd.privtap.domain.entities.UserEntity;
import hr.fer.dsd.privtap.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)

public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User fromEntity(UserEntity entity);

    UserEntity toEntity(User model);

    UserEntity updateEntity(@MappingTarget UserEntity entity, User user);

}

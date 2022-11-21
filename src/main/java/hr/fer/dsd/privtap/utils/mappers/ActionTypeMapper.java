package hr.fer.dsd.privtap.utils.mappers;

import hr.fer.dsd.privtap.domain.entities.ActionTypeEntity;
import hr.fer.dsd.privtap.model.action.ActionType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ActionTypeMapper {

    ActionTypeMapper INSTANCE = Mappers.getMapper(ActionTypeMapper.class);

    ActionType fromEntity(ActionTypeEntity entity);

    ActionTypeEntity toEntity(ActionType model);

    ActionTypeEntity updateEntity(@MappingTarget ActionTypeEntity entity, ActionType actionType);

}
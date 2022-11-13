package hr.fer.dsd.privtap.utils.mappers;

import hr.fer.dsd.privtap.domain.entities.ActionEntity;
import hr.fer.dsd.privtap.model.action.Action;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ActionMapper {
    ActionMapper INSTANCE = Mappers.getMapper(ActionMapper.class);

    Action fromEntity(ActionEntity entity);

    ActionEntity toEntity(Action model);

    ActionEntity updateEntity(@MappingTarget ActionEntity entity, Action action);

}
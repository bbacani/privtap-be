package hr.fer.dsd.privtap.utils.mappers;

import hr.fer.dsd.privtap.domain.entities.TriggerTypeEntity;
import hr.fer.dsd.privtap.model.trigger.TriggerType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface TriggerTypeMapper {

    TriggerTypeMapper INSTANCE = Mappers.getMapper(TriggerTypeMapper.class);

    TriggerType fromEntity(TriggerTypeEntity entity);

    TriggerTypeEntity toEntity(TriggerType model);

    TriggerTypeEntity updateEntity(@MappingTarget TriggerTypeEntity entity, TriggerType triggerType);

}
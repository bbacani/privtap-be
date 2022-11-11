package hr.fer.dsd.privtap.utils.mappers;

import hr.fer.dsd.privtap.domain.entities.TriggerEntity;
import hr.fer.dsd.privtap.model.trigger.Trigger;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface TriggerMapper {
    TriggerMapper INSTANCE = Mappers.getMapper(TriggerMapper.class);

    Trigger fromEntity(TriggerEntity entity);

    TriggerEntity toEntity(Trigger model);

    TriggerEntity updateEntity(@MappingTarget TriggerEntity entity, Trigger trigger);

}
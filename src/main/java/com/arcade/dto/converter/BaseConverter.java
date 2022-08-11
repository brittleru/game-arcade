package com.arcade.dto.converter;

import com.arcade.dto.BaseDto;
import com.arcade.entity.BaseEntity;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Superclass for converting a DTO to Entity and vice-versa
 *
 * @param <DTO>
 * @param <Entity>
 */
public abstract class BaseConverter<DTO extends BaseDto, Entity extends BaseEntity> {

    public abstract DTO fromEntityToDto(Entity entity);

    public abstract Entity fromDtoToEntity(DTO dto);

    public List<DTO> fromEntitiesToDtos(List<Entity> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return Collections.emptyList();
        }
        return entities.stream().map(this::fromEntityToDto).collect(Collectors.toList());
    }

    public List<Entity> fromDtosToEntities(List<DTO> dtos) {
        if (CollectionUtils.isEmpty(dtos)) {
            return Collections.emptyList();
        }
        return dtos.stream().map(this::fromDtoToEntity).collect(Collectors.toList());
    }

}

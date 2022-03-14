package com.arcade.dto.converter;

import com.arcade.dto.RoleDto;
import com.arcade.entity.user.Role;
import com.arcade.exception.MethodNotSupported;
import org.springframework.stereotype.Component;

@Component
public class RoleConverter extends BaseConverter<RoleDto, Role> {

    @Override
    public RoleDto fromEntityToDto(Role entity) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(entity.getId());
        roleDto.setName(entity.getName());

        return roleDto;
    }

    @Override
    public Role fromDtoToEntity(RoleDto dto) {
        throw new MethodNotSupported("Not allowed to convert to Entity.");
    }

}

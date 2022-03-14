package com.arcade.dto.converter;

import com.arcade.dto.UserBasicInfoDto;
import com.arcade.entity.user.User;
import com.arcade.exception.MethodNotSupported;
import org.springframework.stereotype.Component;

@Component
public class UserBasicInfoConverter extends BaseConverter<UserBasicInfoDto, User> {

    @Override
    public UserBasicInfoDto fromEntityToDto(User entity) {
        UserBasicInfoDto userBasicInfoDto = new UserBasicInfoDto();
        userBasicInfoDto.setId(entity.getId());
        userBasicInfoDto.setUsername(entity.getUsername());
        userBasicInfoDto.setFirstName(entity.getFirstName());
        userBasicInfoDto.setLastName(entity.getLastName());
        userBasicInfoDto.setEmail(entity.getEmail());

        return userBasicInfoDto;
    }

    @Override
    public User fromDtoToEntity(UserBasicInfoDto dto) {
        throw new MethodNotSupported("Can't convert to entity, insufficient data");
    }

}

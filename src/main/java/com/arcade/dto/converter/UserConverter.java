package com.arcade.dto.converter;

import com.arcade.dto.UserDto;
import com.arcade.entity.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter extends BaseConverter<UserDto, User> {

    // TODO: this might need more refractoring because it would need a case for updating the user
    // TODO: and one case to



    @Override
    public UserDto fromEntityToDto(User entity) {
        UserDto userDto = new UserDto();
        userDto.setId(entity.getId());
        userDto.setUsername(entity.getUsername());
        userDto.setPassword(entity.getPassword());
        userDto.setFirstName(entity.getFirstName());
        userDto.setLastName(entity.getLastName());
        userDto.setEmail(entity.getEmail());
        userDto.setCreatedAt(entity.getCreatedAt());
        userDto.setUpdatedAt(entity.getUpdatedAt());
        userDto.setRoles(entity.getRoles());

        return userDto;
    }

    @Override
    public User fromDtoToEntity(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setCreatedAt(dto.getCreatedAt());
        user.setUpdatedAt(dto.getUpdatedAt());
        user.setRoles(dto.getRoles());

        return user;
    }


}

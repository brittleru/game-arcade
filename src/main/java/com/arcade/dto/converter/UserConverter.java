package com.arcade.dto.converter;

import com.arcade.dto.UserDto;
import com.arcade.entity.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter extends BaseConverter<UserDto, User> {

    // TODO: this might need more refactoring because it would need a case for updating the user
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
        User user = new User.UserBuilder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .roles(dto.getRoles())
                .build();
        user.setId(dto.getId());

        return user;
    }
}

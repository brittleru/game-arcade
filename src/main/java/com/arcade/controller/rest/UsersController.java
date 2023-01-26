package com.arcade.controller.rest;

import com.arcade.dto.UserBasicInfoDto;
import com.arcade.dto.UserDto;
import com.arcade.dto.converter.UserBasicInfoConverter;
import com.arcade.dto.converter.UserConverter;
import com.arcade.entity.user.User;
import com.arcade.service.rest.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
public class UsersController {

    private final AdminService adminService;
    private final UserConverter userConverter;
    private final UserBasicInfoConverter userBasicInfoConverter;

    @Autowired
    public UsersController(AdminService adminService,
                           UserConverter userConverter,
                           UserBasicInfoConverter userBasicInfoConverter) {
        this.adminService = adminService;
        this.userConverter = userConverter;
        this.userBasicInfoConverter = userBasicInfoConverter;
    }

    @GetMapping("/users")
    public List<UserDto> getUserList() {
        List<User> users = adminService.findAllUsers();
        return userConverter.fromEntitiesToDtos(users);
    }

    @GetMapping("/users-basic-info")
    public List<UserBasicInfoDto> getUserBasicInfoList() {
        List<User> users = adminService.findAllUsers();
        return userBasicInfoConverter.fromEntitiesToDtos(users);
    }

    @GetMapping("/users/{userId}")
    public UserDto getUser(@PathVariable("userId") long id) {
        User user = adminService.findUserById(id);
        return userConverter.fromEntityToDto(user);
    }

    @GetMapping("/users-basic-info/{userId}")
    public UserBasicInfoDto getUserBasicInfo(@PathVariable("userId") long id) {
        User user = adminService.findUserById(id);
        return userBasicInfoConverter.fromEntityToDto(user);
    }

    /**
     * User id is set to 0 just in case the in the request body (in JSON) an ID is not passed
     * This will force a save of a new item in database instead of update
     */
    @PostMapping("/users")
    public UserDto addUser(@Valid @RequestBody UserDto userDto) {
        userDto.setId(0);
        User userEntity = userConverter.fromDtoToEntity(userDto);
        adminService.saveUser(userEntity);

        return userConverter.fromEntityToDto(userEntity);
    }

    @PutMapping("/users")
    public UserDto updateUser(@Valid @RequestBody UserDto userDto) {
        User userEntity = userConverter.fromDtoToEntity(userDto);
        adminService.saveUser(userEntity);

        return userConverter.fromEntityToDto(userEntity);
    }

    @DeleteMapping("/users/{userId}")
    public String deleteUser(@PathVariable("userId") long id) {
        User user = adminService.deleteUserById(id);

        return "Deleted user: " + user.getUsername() + " with ID of " + id;
    }

    @GetMapping("/user/authenticated")
    public String getAuthenticatedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.nonNull(authentication.getName())) {
            return authentication.getName();
        }

        return "User not authenticated.";
    }

}

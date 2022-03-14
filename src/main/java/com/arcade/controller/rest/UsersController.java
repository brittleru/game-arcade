package com.arcade.controller.rest;

import com.arcade.dto.UserBasicInfoDto;
import com.arcade.dto.UserDto;
import com.arcade.dto.converter.UserBasicInfoConverter;
import com.arcade.dto.converter.UserConverter;
import com.arcade.entity.user.User;
import com.arcade.exception.UserNotFoundException;
import com.arcade.service.rest.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public UserDto getUser(@PathVariable("userId") int id) {
        User user = adminService.findUserById(id);
        if (user == null) {
            throw new UserNotFoundException("User with ID of - " + id + " not found");
        }
        return userConverter.fromEntityToDto(user);
    }

    @GetMapping("/users-basic-info/{userId}")
    public UserBasicInfoDto getUserBasicInfo(@PathVariable("userId") int id) {
        User user = adminService.findUserById(id);
        if (user == null) {
            throw new UserNotFoundException("User with ID of - " + id + " not found");
        }
        return userBasicInfoConverter.fromEntityToDto(user);
    }

    /**
     * User id is set to 0 just in case the in the request body (in JSON) an ID is not passed
     * This will force a save of a new item in database instead of update
     */
    @PostMapping("/users")
    public UserDto addUser(@RequestBody UserDto userDto) {
        userDto.setId(0);
        User userEntity = userConverter.fromDtoToEntity(userDto);
        adminService.saveUser(userEntity);

        return userConverter.fromEntityToDto(userEntity);
    }


    // TODO: problem when wanting to update and the user or the email it's the same
    // TODO: as before
    @PutMapping("/users")
    public UserDto updateUser(@RequestBody UserDto userDto) {
        User user = userConverter.fromDtoToEntity(userDto);
        adminService.saveUser(user);

        return userConverter.fromEntityToDto(user);
    }

    @DeleteMapping("/users/{userId}")
    public String deleteUser(@PathVariable("userId") int id) {
        System.out.println("IN DELETE USER");
        User user = adminService.findUserById(id);
        if (user == null) {
            throw new UserNotFoundException("User with ID of - " + id + " not found");
        }
        adminService.deleteUserById(id);
        return "Deleted user: " + user.getUsername() + " with ID of " + id;
    }


}

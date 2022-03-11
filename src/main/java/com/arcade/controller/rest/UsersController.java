package com.arcade.controller.rest;

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

    @Autowired
    public UsersController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/users")
    public List<User> getUserList() {
        return adminService.findAllUsers();
    }

    @GetMapping("/users/{userId}")
    public User getUserList(@PathVariable("userId") int id) {
        User user = adminService.findUserById(id);
        if (user == null) {
            throw new UserNotFoundException("User with ID of - " + id + " not found");
        }
        return user;
    }

    /**
     * User id is set to 0 just in case the in the request body (in JSON) an ID is not passed
     * This will force a save of a new item in database instead of update
     */
    @PostMapping("/users")
    public User addUser(@RequestBody User user) {
        user.setId(0);
        adminService.saveUser(user);
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        adminService.saveUser(user);
        return user;
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

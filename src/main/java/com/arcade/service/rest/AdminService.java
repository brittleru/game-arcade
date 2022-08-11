package com.arcade.service.rest;

import com.arcade.entity.user.User;

import java.util.List;

public interface AdminService {

    List<User> findAllUsers();

    User findUserById(int id);

    void saveUser(User user);

    User deleteUserById(int id);

    List<User> searchUserBy(String field);

    User getUserByUsernameIfDifferentById(String username, int id);

    User getUserByEmailIfDifferentById(String email, int id);

}

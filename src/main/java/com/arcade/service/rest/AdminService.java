package com.arcade.service.rest;

import com.arcade.entity.user.User;

import java.util.List;

public interface AdminService {

    List<User> findAllUsers();

    User findUserById(long id);

    User findUserByUsername(String username);

    void saveUser(User user);

    User deleteUserById(long id);

    List<User> searchUserBy(String field);

    User getUserByUsernameIfDifferentById(String username, long id);

    User getUserByEmailIfDifferentById(String email, long id);

}

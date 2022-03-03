package com.arcade.service.rest;

import com.arcade.entity.user.User;

import java.util.List;

public interface AdminService {

    public List<User> findAllUsers();

    public User findUserById(int id);

    public void saveUser(User user);

    public void deleteUserById(int id);

    public List<User> searchUserBy(String field);

}

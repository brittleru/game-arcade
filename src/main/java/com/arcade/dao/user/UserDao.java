package com.arcade.dao.user;

import com.arcade.entity.user.User;

public interface UserDao {

    User findByUsername(String username);

    void save(User user);

    boolean usernameExists(String username);

    boolean emailExists(String email);

}

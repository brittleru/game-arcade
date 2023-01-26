package com.arcade.dao.user;

import com.arcade.entity.user.User;

public interface UserDao {

    User findByUsername(String username);

    User findById(long id);

    void save(User user);

    boolean usernameExists(String username);

    boolean emailExists(String email);

}

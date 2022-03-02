package com.arcade.dao.user;

import com.arcade.entity.user.User;

public interface UserDao {

    public User findByUsername(String username);

    public void save(User user);

    public boolean usernameExists(String username);

    public boolean emailExists(String email);

}

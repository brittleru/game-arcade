package com.arcade.dao.user;

import com.arcade.entity.user.User;

public interface UserDao {

    public User findByUserName(String userName);

    public void save(User user);

}

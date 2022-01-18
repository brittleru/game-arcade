package com.arcade.service.user;

import com.arcade.validateentity.CheckedUser;
import com.arcade.entity.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    public User findByUserName(String userName);

    public void save(CheckedUser checkedUser);

}

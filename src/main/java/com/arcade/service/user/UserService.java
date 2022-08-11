package com.arcade.service.user;

import com.arcade.entity.user.User;
import com.arcade.validateentity.CheckedUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByUsername(String username);

    void save(CheckedUser checkedUser);

    boolean userNameExists(String userName);

    boolean emailExists(String email);

}

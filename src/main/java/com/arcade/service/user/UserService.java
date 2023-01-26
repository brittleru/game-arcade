package com.arcade.service.user;

import com.arcade.entity.user.User;
import com.arcade.validation.entity.CheckedUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByUsername(String username);

    User findById(long id);

    void save(CheckedUser checkedUser);

    void updateNames(User user, String firstName, String lastName);

    boolean userNameExists(String userName);

    boolean emailExists(String email);

}

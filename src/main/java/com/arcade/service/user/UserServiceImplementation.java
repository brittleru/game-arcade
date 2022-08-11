package com.arcade.service.user;

import com.arcade.dao.user.RoleDao;
import com.arcade.dao.user.UserDao;
import com.arcade.entity.user.Role;
import com.arcade.entity.user.User;
import com.arcade.validateentity.CheckedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class UserServiceImplementation implements UserService {

    private final static Logger logger = Logger.getLogger(UserServiceImplementation.class.getName());

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImplementation(UserDao userDao, RoleDao roleDao, BCryptPasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    // TODO: change every set with builder
    @Override
    @Transactional
    public void save(CheckedUser checkedUser) {
        User user = new User();
        user.setUsername(checkedUser.getUserName());
        user.setPassword(passwordEncoder.encode(checkedUser.getPassword()));
        user.setFirstName(checkedUser.getFirstName());
        user.setLastName(checkedUser.getLastName());
        user.setEmail(checkedUser.getEmail());
        user.setRoles(List.of(roleDao.findRoleByName("ROLE_NORMAL")));

        userDao.save(user);
    }

    @Override
    @Transactional
    public boolean userNameExists(String userName) {
        return userDao.usernameExists(userName);
    }

    @Override
    @Transactional
    public boolean emailExists(String email) {
        return userDao.emailExists(email);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);

        if (user == null) {
            logger.warning("Non-existent username");
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles())
        );

    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

}

package com.arcade.service.rest;

import com.arcade.dao.user.RoleDao;
import com.arcade.dao.user.UserDao;
import com.arcade.dao.user.UserDaoImplementation;
import com.arcade.entity.user.User;
import com.arcade.exception.EmailTakenException;
import com.arcade.exception.UserNotFoundException;
import com.arcade.exception.UsernameTakenException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.List;
import java.util.logging.Logger;

@Service
public class AdminServiceImplementation implements AdminService {

    // TODO: add Optional<?> to all possible methods in service classes

    private final static Logger logger = Logger.getLogger(UserDaoImplementation.class.getName());

    private final EntityManager entityManager;
    private final RoleDao roleDao;
    private final UserDao userDao;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdminServiceImplementation(EntityManager entityManager, RoleDao roleDao, UserDao userDao, BCryptPasswordEncoder passwordEncoder) {
        this.entityManager = entityManager;
        this.roleDao = roleDao;
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public List<User> findAllUsers() {
        Session session = entityManager.unwrap(Session.class);
        Query<User> query = session.createQuery("from User order by id", User.class);
        if (query.getResultList() == null) {
            logger.info("No users found");
            throw new UserNotFoundException("No users found.");
        }
        return query.getResultList();
    }

    @Override
    @Transactional
    public User findUserById(int id) {
        Session session = entityManager.unwrap(Session.class);
        Query<User> query = session.createQuery("from User where id=:tempId", User.class);
        query.setParameter("tempId", id);

        User user = null;
        try {
            user = query.getSingleResult();
        } catch (Exception e) {
            logger.warning("Can't find user with ID of " + id);
            logger.warning(e.getMessage());
        }
        if (user == null) {
            throw new UserNotFoundException("User with ID of - " + id + " not found");
        }

        return user;
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        Session session = entityManager.unwrap(Session.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRoles() == null) {
            logger.info("No roles assigned for user " + user.getUsername() + ", assigning default role.");
            user.setRoles(List.of(roleDao.findRoleByName("ROLE_NORMAL")));
        }
        if (user.getId() != 0) {
            if (getUserByEmailIfDifferentById(user.getEmail(), user.getId()) != null) {
                logAndThrowEmailTaken(user.getEmail());
            } else if (getUserByUsernameIfDifferentById(user.getUsername(), user.getId()) != null) {
                logAndThrowUsernameTaken(user.getUsername());
            }
            session.saveOrUpdate(user);
            logger.info("Updated user " + user.getUsername());
            return;
        }

        if (userDao.emailExists(user.getEmail())) {
            logAndThrowEmailTaken(user.getEmail());
        } else if (userDao.usernameExists(user.getUsername())) {
            logAndThrowUsernameTaken(user.getUsername());
        }
        session.saveOrUpdate(user);
        logger.info("Added user " + user.getUsername());
    }

    @Override
    @Transactional
    public User deleteUserById(int id) {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("delete from User where id=:userId");
        query.setParameter("userId", id);
        User user = findUserById(id);
        if (user == null) {
            throw new UserNotFoundException("User with ID of - " + id + " not found");
        }
        query.executeUpdate();

        return user;
    }

    @Override
    @Transactional
    public List<User> searchUserBy(String field) {
        return null;
    }

    @Override
    @Transactional
    public User getUserByUsernameIfDifferentById(String username, int id) {
        Session session = entityManager.unwrap(Session.class);
        Query<User> userNameExistsQuery = session
                .createQuery("from User where id!=:tempId and username=:tempUsername", User.class);
        userNameExistsQuery.setParameter("tempId", id);
        userNameExistsQuery.setParameter("tempUsername", username);
        User userWithUsername = null;
        try {
            userWithUsername = userNameExistsQuery.getSingleResult();
        } catch (Exception e) {
            logger.warning("Can't find user with ID of " + id + " and Username of " + username);
            logger.warning(e.getMessage());
        }

        return userWithUsername;
    }

    @Override
    @Transactional
    public User getUserByEmailIfDifferentById(String email, int id) {
        Session session = entityManager.unwrap(Session.class);
        Query<User> emailExistsQuery = session
                .createQuery("from User where id!=:tempId and email=:tempEmail", User.class);
        emailExistsQuery.setParameter("tempId", id);
        emailExistsQuery.setParameter("tempEmail", email);
        User userWithEmail = null;
        try {
            userWithEmail = emailExistsQuery.getSingleResult();
        } catch (Exception e) {
            logger.warning("Can't find user with ID of " + id + " and Email of " + email);
            logger.warning(e.getMessage());
        }

        return userWithEmail;
    }

    private void logAndThrowUsernameTaken(String username) {
        logger.warning("Username " + username + " already exists");
        throw new UsernameTakenException("Username taken");
    }

    private void logAndThrowEmailTaken(String email) {
        logger.warning("Email " + email + " already exists");
        throw new EmailTakenException("Email taken");
    }

}

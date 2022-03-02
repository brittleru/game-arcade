package com.arcade.dao.user;

import com.arcade.entity.user.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.logging.Logger;

@Repository
public class UserDaoImplementation implements UserDao {

    private final static Logger logger = Logger.getLogger(UserDaoImplementation.class.getName());

    private EntityManager entityManager;

    @Autowired
    public UserDaoImplementation(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User findByUsername(String username) {
        Session session = entityManager.unwrap(Session.class);
        Query<User> query = session.createQuery("from User where username=:tempName", User.class);
        query.setParameter("tempName", username);

        User user = null;

        try {
            user = query.getSingleResult();
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
        return user;
    }

    @Override
    public void save(User user) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(user);
    }

    @Override
    public boolean usernameExists(String username) {
        User user = findByUsername(username);
        return user != null;
    }

    @Override
    public boolean emailExists(String email) {
        Session session = entityManager.unwrap(Session.class);
        Query<User> query = session.createQuery("from User where email=:tempEmail", User.class);
        query.setParameter("tempEmail", email);

        User user = null;
        try {
            user = query.getSingleResult();
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
        return user != null;
    }
}

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
    public User findByUserName(String userName) {

        Session session = entityManager.unwrap(Session.class);

        Query<User> query1 = session.createQuery("from User where userName='brittle'", User.class);
        User test = query1.getSingleResult();
        logger.info("\n\n" + test.toString() + "\n\n");

        Query<User> query = session.createQuery("from User where userName:=tempName", User.class);
        query.setParameter("tempName", userName);


        User user = null;

        try {
            user = query.getSingleResult();
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
        logger.info(String.valueOf(user));
        return user;
    }

    @Override
    public void save(User user) {

        Session session = entityManager.unwrap(Session.class);

//
//

        session.saveOrUpdate(user);

    }
}

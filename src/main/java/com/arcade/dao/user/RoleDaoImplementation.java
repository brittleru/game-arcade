package com.arcade.dao.user;

import com.arcade.entity.user.Role;
import com.arcade.entity.user.User;
import org.apache.juli.logging.Log;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.logging.Logger;

@Repository
public class RoleDaoImplementation implements RoleDao {

    private final static Logger logger = Logger.getLogger(RoleDaoImplementation.class.getName());

    private final EntityManager entityManager;

    @Autowired
    public RoleDaoImplementation(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Role findRoleByName(String roleName) {

        Session session = entityManager.unwrap(Session.class);

        Query<Role> query = session.createQuery("from Role where name=:roleName", Role.class);
        query.setParameter("roleName", roleName);

        Role role = null;

        try {
            role = query.getSingleResult();
        }
        catch (Exception e) {
            logger.warning(e.getMessage());
        }

        logger.info("\n\n" + query.getSingleResult().toString() + "\n\n");


        return role;

    }

}

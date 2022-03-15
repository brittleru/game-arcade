package com.arcade.service.user;

import com.arcade.entity.user.Role;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@Service
public class RoleServiceImplementation implements RoleService {

    private final static Logger logger = Logger.getLogger(RoleServiceImplementation.class.getName());

    private final EntityManager entityManager;

    @Autowired
    public RoleServiceImplementation(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public List<Role> findAllRoles() {
        Session session = entityManager.unwrap(Session.class);
        Query<Role> query = session.createQuery("from Role order by id", Role.class);
        if (query.getResultList() == null) {
            logger.warning("No roles found.");
            // TODO: throw new role not found
        }
        return query.getResultList();
    }
}

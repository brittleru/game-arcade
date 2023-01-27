package com.arcade.service.highscore;

import com.arcade.entity.HighScore;
import com.arcade.entity.user.User;
import com.arcade.exception.UserNotFoundException;
import com.arcade.service.user.UserService;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@Service
public class HighScoreServiceImplementation implements HighScoreService {
    private final static Logger logger = Logger.getLogger(HighScoreServiceImplementation.class.getName());
    private final EntityManager entityManager;
    private final UserService userService;

    @Autowired
    public HighScoreServiceImplementation(EntityManager entityManager, UserService userService) {
        this.entityManager = entityManager;
        this.userService = userService;
    }

    @Override
    @Transactional
    public List<HighScore> findAllHighScores() {
        Session session = entityManager.unwrap(Session.class);
        Query<HighScore> query = session.createQuery("from HighScore order by id", HighScore.class);
        if (query.getResultList() == null) {
            logger.info("No high scores found");
        }
        return query.getResultList();
    }

    @Override
    @Transactional
    public List<HighScore> findHighScoresByUserId(long userId) {
        Session session = entityManager.unwrap(Session.class);
        Query<HighScore> query = session.createQuery("from HighScore where user_id=:tempUserId", HighScore.class);
        query.setParameter("tempUserId", userId);

        if (query.getResultList() == null) {
            logger.info("No high scores found");
        }

        return query.getResultList();
    }

    @Override
    @Transactional
    public HighScore findHighScoreById(long id) {
        Session session = entityManager.unwrap(Session.class);
        Query<HighScore> query = session.createQuery("from HighScore where id=:tempId", HighScore.class);
        query.setParameter("tempId", id);

        HighScore highScore = null;
        try {
            highScore = query.getSingleResult();
        } catch (Exception e) {
            logger.warning("Can't find HighScore with ID of " + id);
            logger.warning(e.getMessage());
        }
        return highScore;
    }

    @Override
    @Transactional
    public HighScore findHighScoreByGameAndUsername(String game, String username) {
        Session session = entityManager.unwrap(Session.class);
        User user = userService.findByUsername(username);

        if (user == null) {
            throw new UserNotFoundException("No user with username " + username + " found.");
        }

        Query<HighScore> query = session.createQuery("from HighScore where game=:tempGame and user_id=:tempUserId", HighScore.class);
        query.setParameter("tempGame", game);
        query.setParameter("tempUserId", user.getId());

        HighScore highScore = null;
        try {
            highScore = query.getSingleResult();
        } catch (Exception e) {
            logger.warning("Can't find HighScore with game " + game + " and Username of " + username);
            logger.warning(e.getMessage());
        }

        return highScore;
    }

    @Override
    @Transactional
    public void saveHighScore(HighScore highScore) {
        Session session = entityManager.unwrap(Session.class);

        if (highScore.getId() != 0) {
            User user = userService.findById(highScore.getUser().getId());
            if (user == null) {
                throw new UserNotFoundException("Invalid user with for high score: " + highScore);
            }
            for (HighScore userHighScore: user.getHighScores()) {
                if (userHighScore.getGame().equals(highScore.getGame())) {
                    if (highScore.getScore() <= userHighScore.getScore()) {
                        return;
                    }
                }
            }
            highScore.setUser(user);
            session.evict(highScore);
            session.merge(highScore);
            logger.info(String.format("Updated new high score (%s) for (%s) at game (%s).",
                    highScore.getScore(), highScore.getUser().getUsername(), highScore.getGame()));
            return;
        }

        User user = userService.findById(highScore.getUser().getId());
        if (user == null) {
            throw new UserNotFoundException("Invalid user with for high score: " + highScore);
        }

        HighScore alreadyOneHighScoreForUserGame = findHighScoreByGameAndUsername(highScore.getGame(), user.getUsername());

        if (alreadyOneHighScoreForUserGame != null) {
            throw new RuntimeException("There is already one high score for this game, unable to save. Use update");
        }

        session.saveOrUpdate(highScore);
        logger.info(String.format("Saved high score (%s) for (%s) at game (%s).",
                highScore.getScore(), highScore.getUser().getUsername(), highScore.getGame()));
    }

    @Override
    @Transactional
    public HighScore deleteHighScoreByGameAndUsername(String game, String username) {
        Session session = entityManager.unwrap(Session.class);
        User user = userService.findByUsername(username);

        if (user == null) {
            throw new UserNotFoundException("No user with username " + username + " found.");
        }

        Query query = session.createQuery("delete from HighScore where game=:tempGame and user_id=:tempUserId");
        query.setParameter("tempGame", game);
        query.setParameter("tempUserId", user.getId());
        HighScore highScore = findHighScoreByGameAndUsername(game, username);
        if (highScore == null) {
            throw new UserNotFoundException("HighScore with game - " + game + " and username of " + username + " not found");
        }
        query.executeUpdate();
        return highScore;
    }
}

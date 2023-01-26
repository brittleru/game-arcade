package com.arcade.service.highscore;

import com.arcade.entity.HighScore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HighScoreServiceImplementation implements HighScoreService {


    @Override
    public List<HighScore> findAllHighScores() {
        return null;
    }

    @Override
    public List<HighScore> findHighScoresByUsername(String username) {
        return null;
    }

    @Override
    public HighScore findHighScoreById(long id) {
        return null;
    }

    @Override
    public HighScore findHighScoreByGameAndUsername(String game, String username) {
        return null;
    }

    @Override
    public void saveHighScore(HighScore highScore) {

    }

    @Override
    public HighScore deleteHighScoreByGameAndUsername(String game, String username) {
        return null;
    }
}

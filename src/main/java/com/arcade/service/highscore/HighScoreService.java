package com.arcade.service.highscore;

import com.arcade.entity.HighScore;

import java.util.List;

public interface HighScoreService {

    List<HighScore> findAllHighScores();

    List<HighScore> findHighScoresByUsername(String username);

    HighScore findHighScoreById(long id);

    HighScore findHighScoreByGameAndUsername(String game, String username);

    void saveHighScore(HighScore highScore);

    HighScore deleteHighScoreByGameAndUsername(String game, String username);
}

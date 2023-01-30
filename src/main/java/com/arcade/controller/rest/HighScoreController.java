package com.arcade.controller.rest;

import com.arcade.dto.HighScoreDto;
import com.arcade.dto.converter.HighScoreConverter;
import com.arcade.entity.HighScore;
import com.arcade.service.highscore.HighScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/high")
public class HighScoreController {

    private final HighScoreService highScoreService;
    private final HighScoreConverter highScoreConverter;

    @Autowired
    public HighScoreController(HighScoreService highScoreService, HighScoreConverter highScoreConverter) {
        this.highScoreService = highScoreService;
        this.highScoreConverter = highScoreConverter;
    }

    @GetMapping("/scores")
    public List<HighScoreDto> getAllHighScores() {
        List<HighScore> highScores = highScoreService.findAllHighScores();
        return highScoreConverter.fromEntitiesToDtos(highScores);
    }

    @GetMapping("/scores/{userId}")
    public List<HighScoreDto> getAllHighScoresForUser(@PathVariable("userId") long id) {
        List<HighScore> highScores = highScoreService.findHighScoresByUserId(id);
        return highScoreConverter.fromEntitiesToDtos(highScores);
    }

    @GetMapping("/score/{highScoreId}")
    public HighScoreDto getHighScore(@PathVariable("highScoreId") long id) {
        HighScore highScore = highScoreService.findHighScoreById(id);
        return highScoreConverter.fromEntityToDto(highScore);
    }

    @GetMapping("/score/{game}/{username}")
    public ResponseEntity<HighScoreDto> getHighScore(@PathVariable("game") String game, @PathVariable("username") String username) {
        HighScore highScore = highScoreService.findHighScoreByGameAndUsername(game, username);
        if (highScore == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        highScore.getUser().setUserImage(null);
        return ResponseEntity.ok(highScoreConverter.fromEntityToDto(highScore));
    }


    @PostMapping("/scores")
    public HighScoreDto addHighScore(@Valid @RequestBody HighScoreDto highScoreDto) {
        highScoreDto.setId(0);
        HighScore highScore = highScoreConverter.fromDtoToEntity(highScoreDto);
        highScoreService.saveHighScore(highScore);

        return highScoreConverter.fromEntityToDto(highScore);
    }

    @PutMapping("/scores")
    public HighScoreDto updateHighScore(@Valid @RequestBody HighScoreDto highScoreDto) {
        HighScore highScore = highScoreConverter.fromDtoToEntity(highScoreDto);
        highScoreService.saveHighScore(highScore);

        return highScoreConverter.fromEntityToDto(highScore);
    }

    @DeleteMapping("/score/{game}/{username}")
    public String deleteHighScore(@PathVariable("game") String game, @PathVariable("username") String username) {
        HighScore highScore = highScoreService.deleteHighScoreByGameAndUsername(game, username);

        return "Deleted high score for user: " + username + " at " + highScore.getGame() + " game.";
    }

}

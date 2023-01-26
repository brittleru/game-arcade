package com.arcade.dto.converter;

import com.arcade.dto.HighScoreDto;
import com.arcade.entity.HighScore;
import org.springframework.stereotype.Component;

@Component
public class HighScoreConverter extends BaseConverter<HighScoreDto, HighScore> {

    @Override
    public HighScoreDto fromEntityToDto(HighScore entity) {
        HighScoreDto highScoreDto = new HighScoreDto();
        highScoreDto.setId(entity.getId());
        highScoreDto.setGame(entity.getGame());
        highScoreDto.setScore(entity.getScore());
        highScoreDto.setUser(entity.getUser());
        highScoreDto.setCreatedAt(entity.getCreatedAt());
        highScoreDto.setUpdatedAt(entity.getUpdatedAt());

        return highScoreDto;
    }

    @Override
    public HighScore fromDtoToEntity(HighScoreDto dto) {
        HighScore highScore = new HighScore();
        highScore.setId(dto.getId());
        highScore.setGame(dto.getGame());
        highScore.setScore(dto.getScore());
        highScore.setUser(dto.getUser());
        highScore.setCreatedAt(dto.getCreatedAt());
        highScore.setUpdatedAt(dto.getUpdatedAt());

        return highScore;
    }
}

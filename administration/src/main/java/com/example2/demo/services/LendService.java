package com.example2.demo.services;

import com.example2.demo.dao.GameRepository;
import com.example2.demo.model.GameEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LendService {

    private GameRepository gameRepository;

    public LendService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Page<GameEntity> getHistoryLendGames(Long userId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return gameRepository.findHistoricalLendGamesByUser(userId, pageable);
    }
}

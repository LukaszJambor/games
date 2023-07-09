package com.example2.demo.listener;

import com.example2.demo.converters.GameEntityGameDataMapper;
import com.example2.demo.data.GameData;
import com.example2.demo.model.GameEntity;
import com.example2.demo.services.GameService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class GameImportListener {

    private GameService gameService;
    private GameEntityGameDataMapper gameEntityGameDataMapper;

    public GameImportListener(GameService gameService, GameEntityGameDataMapper gameEntityGameDataMapper) {
        this.gameService = gameService;
        this.gameEntityGameDataMapper = gameEntityGameDataMapper;
    }

    @KafkaListener(topics = "export-games", groupId = "gamesImport")
    public void importGame(ConsumerRecord<String, GameData> consumerRecord) {
        GameData gameData = consumerRecord.value();
        GameEntity gameEntity = gameEntityGameDataMapper.toEntity(gameData);
        gameService.addGame(gameEntity);
    }
}
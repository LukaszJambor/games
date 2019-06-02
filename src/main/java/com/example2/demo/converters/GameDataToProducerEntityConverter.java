package com.example2.demo.converters;

import com.example2.demo.data.GameData;
import com.example2.demo.model.ProducerEntity;
import org.springframework.stereotype.Component;

/**
 * Created by USER on 31.05.2019.
 */
@Component
public class GameDataToProducerEntityConverter {

    public ProducerEntity convert(GameData gameData) {
        ProducerEntity producerEntity = new ProducerEntity();
        producerEntity.setProducerName(gameData.getProducerName());
        return producerEntity;
    }
}

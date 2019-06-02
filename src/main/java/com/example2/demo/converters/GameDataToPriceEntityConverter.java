package com.example2.demo.converters;

import com.example2.demo.data.GameData;
import com.example2.demo.model.PriceEntity;
import org.springframework.stereotype.Component;

/**
 * Created by USER on 01.06.2019.
 */
@Component
public class GameDataToPriceEntityConverter {

    public PriceEntity convert(GameData gameData){
        PriceEntity priceEntity = new PriceEntity();
        priceEntity.setCurrency(gameData.getCurrency());
        priceEntity.setPrice(gameData.getPrice());
        return priceEntity;
    }
}

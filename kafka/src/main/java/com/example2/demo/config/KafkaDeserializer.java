package com.example2.demo.config;

import com.example2.demo.data.GameData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

public class KafkaDeserializer implements Deserializer<GameData> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public GameData deserialize(String s, byte[] bytes) {
        try {
            return objectMapper.readValue(new String(bytes, "UTF-8"), GameData.class);
        } catch (IOException e) {
            throw new SerializationException("Error when deserializing byte[] to GameData");
        }
    }

    @Override
    public void close() {

    }
}

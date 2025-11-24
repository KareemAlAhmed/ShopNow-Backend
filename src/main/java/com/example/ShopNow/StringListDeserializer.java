package com.example.ShopNow;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class StringListDeserializer extends JsonDeserializer<List<String>> {
    @Override
    public List<String> deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return new ObjectMapper().readValue(value, List.class);
        } catch (Exception e) {
            return List.of(value);
        }
    }
}
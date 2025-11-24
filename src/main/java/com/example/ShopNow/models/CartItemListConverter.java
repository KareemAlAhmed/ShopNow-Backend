package com.example.ShopNow.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Converter
public class CartItemListConverter implements AttributeConverter<List<CartItem>, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<CartItem> cartItems) {
        try {
            return objectMapper.writeValueAsString(cartItems);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting cart items to JSON", e);
        }
    }

    @Override
    public List<CartItem> convertToEntityAttribute(String dbData) {
        try {
            if (dbData == null || dbData.isEmpty()) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(dbData,
                    new TypeReference<List<CartItem>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Error converting JSON to cart items", e);
        }
    }
}
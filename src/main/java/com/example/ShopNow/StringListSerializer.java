package com.example.ShopNow;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.List;

public class StringListSerializer extends JsonSerializer<List<String>> {
    @Override
    public void serialize(List<String> value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeString(new ObjectMapper().writeValueAsString(value));
        }
    }
}

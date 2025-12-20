package com.example.ShopNow.Services;

import com.example.ShopNow.Models.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class JsonReaderService {

    private final ObjectMapper objectMapper = new ObjectMapper();


    // Read JSON file into List
    public List<Product> readUsersFromFile(String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Product.class));
    }

    // Read JSON from resources folder

}
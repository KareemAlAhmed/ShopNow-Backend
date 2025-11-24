package com.example.ShopNow.models.dao;

import com.example.ShopNow.models.Product;

import java.io.IOException;
import java.util.List;

public interface ProductDAO {

    public List<Product> loadAllProducts() throws IOException;
    public Product getProdById(int id);
    public List<Product> getAllProduct(String prodType);
    public List<Product> getRelatedProd(String subCategory);
}

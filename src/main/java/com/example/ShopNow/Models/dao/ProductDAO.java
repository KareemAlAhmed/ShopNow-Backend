package com.example.ShopNow.Models.dao;

import com.example.ShopNow.Models.Product;

import java.io.IOException;
import java.util.List;

public interface ProductDAO {

    public List<Product> loadAllProducts() throws IOException;
    public Product getProdById(int id);
    public List<Product> getAllProductByCat(String prodType);
    public List<Product> getRelatedProd(String subCategory);
    public boolean updateProdQt(int prodId,int quatityToRemove);
    public List<Product> searchProds(String search);
    public List<Product> searchForProdsByFiler(String filter,String value);
    public  void  updateProduct(Product prod);
    public  void  upDatabase();
}

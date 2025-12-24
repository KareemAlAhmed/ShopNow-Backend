package com.example.ShopNow.Models.dao;

import com.example.ShopNow.Models.Category;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CategoryDAO {
    public void loadAllCat();
    public Map<String, List<String>> loadAllSubcategories() throws IOException;

    public List<Category> getAllCats();
}

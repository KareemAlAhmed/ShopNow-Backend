package com.example.ShopNow.Models.dao;

import com.example.ShopNow.Models.Category;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class CategoryDAOImp implements CategoryDAO{
        EntityManager entityManager;

        @Autowired
        public CategoryDAOImp(EntityManager entityManager){
            this.entityManager=entityManager;
        }
    @Transactional
    public void loadAllCat(){
        List<String> allCats = new ArrayList<>(Arrays.asList("Computer-Parts", "Networking", "Fashion","Laptops","Tablet","Desktops","Gaming","Mobile"));
        for(String name: allCats){
            Category category=new Category();
            category.setName(name);
            entityManager.persist(category);
        }
    }
}

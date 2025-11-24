package com.example.ShopNow.models.dao;

import com.example.ShopNow.models.Product;
import com.example.ShopNow.service.JsonReaderService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.*;
@Repository
public class ProductDAOImp implements ProductDAO{
    @Autowired
    private JsonReaderService jsonReaderService;
    EntityManager entityManager;

    @Autowired
    public  ProductDAOImp(EntityManager entityManager){
        this.entityManager=entityManager;
    }
    @Transactional
    public List<Product> loadAllProducts() throws IOException {
        Map<String, String> productTypes = new HashMap<>();

        productTypes.put("gaming", "gaming");
        productTypes.put("desktop", "desktop");
        productTypes.put("pcComp", "computer-components");
        productTypes.put("kidFashion", "kid-fashion");
        productTypes.put("laptops", "laptops");
        productTypes.put("menFashion", "men-fashion");
        productTypes.put("mobile", "mobile");
        productTypes.put("networking", "networking");
        productTypes.put("tablets", "tablets");
        productTypes.put("womenFashion", "women-fashion");
        List<Product> allProds=new ArrayList<>();
        for (Map.Entry<String, String> entry : productTypes.entrySet()) {
            List<Product> prods=jsonReaderService.readUsersFromFile("src/main/resources/data/"+entry.getValue()+".json");
            allProds.addAll(prods);
        }
        for(Product prod: allProds){
            entityManager.persist(prod);
        }
        return  allProds;
    }

    public Product getProdById(int id){
        return entityManager.find(Product.class,id);
    }

    public List<Product> getAllProduct(String prodType){
        if(!prodType.equals("all")){
            List<Product> allProds=entityManager.createQuery("FROM Product WHERE categoryName = :theCat").setParameter("theCat",prodType).getResultList();
            return allProds;
        }else{
            List<Product> allProds=entityManager.createQuery("FROM Product").getResultList();
            return allProds;
        }
    }

    public List<Product> getRelatedProd(String subCategory){
        List<Product> prods=entityManager.createQuery("FROM Product WHERE subcategoryName = :subC").setParameter("subC",subCategory).setMaxResults(5).getResultList();
        return  prods;
    }
}

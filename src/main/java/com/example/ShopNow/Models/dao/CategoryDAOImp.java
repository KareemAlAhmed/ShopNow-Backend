package com.example.ShopNow.Models.dao;

import com.example.ShopNow.Models.Category;
import com.example.ShopNow.Models.Product;
import com.example.ShopNow.Models.Subcategory;
import com.example.ShopNow.Repositories.SubcategoryRepository;
import com.example.ShopNow.Services.JsonReaderService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;

@Repository
public class CategoryDAOImp implements CategoryDAO{
        EntityManager entityManager;
    SubcategoryRepository subcategoryRepository;
    @Autowired
    private JsonReaderService jsonReaderService;
        @Autowired
        public CategoryDAOImp(EntityManager entityManager, SubcategoryRepository subcategoryRepository){
            this.entityManager=entityManager;
            this.subcategoryRepository=subcategoryRepository;
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



    @Transactional
    public Map<String, List<String>> loadAllSubcategories() throws IOException {
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
        Map<String, List<String>> subs = new HashMap<>();
        for(Product prod: allProds){
            //entityManager.persist(prod);
            if(subs.containsKey(prod.getCategoryName())){
                if(!subs.get(prod.getCategoryName()).contains(prod.getSubcategoryName())){
                    subs.get(prod.getCategoryName()).add(prod.getSubcategoryName());
                }

            }else{
                if(prod.getCategoryName().equals("Laptops")){
                    continue;
                }
                subs.put(prod.getCategoryName(),new ArrayList<>());
                subs.get(prod.getCategoryName()).add(prod.getSubcategoryName());
            }
        }

        for (Map.Entry<String, List<String>> entry : subs.entrySet()) {
            String key = entry.getKey();
            List<String> value = entry.getValue();
            Category cate=(Category)entityManager.createQuery("FROM Category WHERE name = :theCat").setParameter("theCat",key).getSingleResult();
            for (String str: value){
                Subcategory subcategory=new Subcategory();
                subcategory.setName(str);
                subcategory.setCategory(cate);
                subcategoryRepository.save(subcategory);
            }
        }

        return  subs;
    }

    public List<Category> getAllCats(){
            return entityManager.createQuery("FROM Category ").getResultList();
    }
}

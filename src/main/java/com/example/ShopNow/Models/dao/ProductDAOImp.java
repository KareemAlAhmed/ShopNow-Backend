package com.example.ShopNow.Models.dao;

import com.example.ShopNow.Models.Product;
import com.example.ShopNow.Services.JsonReaderService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    @Transactional
    public  void  updateProduct(Product prod){ entityManager.merge(prod);}

    public List<Product> getAllProductByCat(String prodType){
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

    @Transactional
    public boolean updateProdQt(int prodId,int quatityToRemove){
        Product prod=this.getProdById(prodId);
        if(prod !=null){
            int prodQt=Integer.parseInt(prod.getQuantity());
            if(prodQt>= quatityToRemove){
                int newQt=prodQt-quatityToRemove;
                prod.setQuantity(String.valueOf(newQt));
                entityManager.merge(prod);
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }


    public List<Product> searchProds(String search){
        System.out.println(search);
        List<Product> prods=entityManager.createQuery("FROM Product WHERE name LIKE :searchC").setParameter("searchC", "%" + search + "%").getResultList();
        return prods;
    }
}

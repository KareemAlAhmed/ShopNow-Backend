package com.example.ShopNow.models;

import com.example.ShopNow.models.dao.CategoryDAO;
import com.example.ShopNow.models.dao.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {
    public ProductDAO productDAO;
    public CategoryDAO categoryDAO;

    @Autowired
    public ProductService(ProductDAO productDAO,CategoryDAO categoryDAO){
        this.productDAO=productDAO;
        this.categoryDAO=categoryDAO;
    }

    public void loadProductsData() throws IOException {
        categoryDAO.loadAllCat();
        productDAO.loadAllProducts();
    }

    public ResponseEntity<?> getProductById(int id){
        Product prod=productDAO.getProdById(id);
        if (prod == null){
            return ResponseEntity.ok(Map.of(
                    "status","error",
                    "error","Product Doesnt Exist!"
            ));
        }else{
            List<Product> relatedProd=productDAO.getRelatedProd(prod.getSubcategoryName());
            return ResponseEntity.ok(Map.of(
                    "status","success",
                    "prod",prod,
                    "relatedProds",relatedProd
            ));
        }
    }

    public ResponseEntity<?> getAllProduct(String prodType){
        List<Product> prods=productDAO.getAllProduct(prodType);
        return ResponseEntity.ok(Map.of(
           "status","success",
           "prods",prods
        ));
    }
}

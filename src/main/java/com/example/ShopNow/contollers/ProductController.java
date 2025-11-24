package com.example.ShopNow.contollers;

import com.example.ShopNow.models.Product;
import com.example.ShopNow.models.ProductService;
import com.example.ShopNow.models.dao.ProductDAO;
import com.example.ShopNow.service.JsonReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private JsonReaderService jsonReaderService;
    public ProductService prodService;
    public ProductDAO productDAO;

    @Autowired
    public ProductController(ProductDAO productDAO,ProductService prodService){
        this.productDAO=productDAO;
        this.prodService=prodService;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getProductById(@PathVariable int id){
        return prodService.getProductById(id);
    }

    @GetMapping("/get/products/{prodType}")
    public ResponseEntity<?> getProducts(@PathVariable String prodType){
        return prodService.getAllProduct(prodType);

    }


    @GetMapping("/loadProducts")
    public List<Product> loadAllProducts() throws IOException {
        return productDAO.loadAllProducts();
    }

    @GetMapping("/getListOfCategories")
    public Set<String> getListOfCat() throws IOException {
        List<Product> products=productDAO.loadAllProducts();
        Set<String> uniqueCategories = new HashSet<>();

        for (Product product : products) {
            uniqueCategories.add(product.getCategoryName());
        }

        return uniqueCategories;
    }

    @GetMapping("/loadAllData")
    public void loadAllData() throws IOException {
        prodService.loadProductsData();

    }


}

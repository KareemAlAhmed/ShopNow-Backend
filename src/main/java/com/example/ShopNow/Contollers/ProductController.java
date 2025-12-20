package com.example.ShopNow.Contollers;

import com.example.ShopNow.Services.JsonReaderService;
import com.example.ShopNow.Services.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private JsonReaderService jsonReaderService;
    public WebService webService;
    @Autowired
    public ProductController(WebService webService){
        this.webService=webService;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getProductById(@PathVariable int id){
        return webService.getProductById(id);
    }

    @GetMapping("/get/product/{prodType}")
    public ResponseEntity<?> getProducts(@PathVariable String prodType){
        return webService.getAllProductByCat(prodType);
    }

    @GetMapping("/loadAllData")
    public void loadAllData() throws IOException {
        webService.loadProductsData();
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchForProds(@RequestParam("search") String search){

        return webService.searchForProds(search);
    }



}

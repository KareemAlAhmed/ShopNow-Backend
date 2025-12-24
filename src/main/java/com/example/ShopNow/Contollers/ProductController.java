package com.example.ShopNow.Contollers;

import com.example.ShopNow.Services.JsonReaderService;
import com.example.ShopNow.Models.Product;
import com.example.ShopNow.Services.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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

    @GetMapping("/cats")
    public ResponseEntity<?> getCats(){
        return ResponseEntity.ok(Map.of(
                "status","success",
                "cats", webService.getAllCate()
        ));
    }

    @GetMapping("/getStartupData")
    public ResponseEntity<?> getStartupData(){
        Map<?, ?> cateResponseMap = (Map<?, ?>)this.getCats().getBody();
        Object cats = cateResponseMap.get("cats");

        Map<?, ?> prodsResponseMap = (Map<?, ?>)this.getProducts("all").getBody();
        Object prods = prodsResponseMap.get("prods");

        Map<String,Integer> brands=new HashMap<>();
        for (Product product: (List<Product>) prods){
            if(brands.containsKey(product.getBrandName())){
                int currentCount = brands.get(product.getBrandName());
                brands.put(product.getBrandName(), currentCount + 1);

            }else{
                brands.put(product.getBrandName(), 1);
            }
        }



        return ResponseEntity.ok(Map.of(
                "status","success",
                "prods", prods,
                "categories",cats,
                "brands", brands.entrySet().stream()
                        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (e1, e2) -> e1,
                                LinkedHashMap::new
                        ))
        ));
    }

}

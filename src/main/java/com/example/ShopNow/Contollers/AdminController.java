package com.example.ShopNow.Contollers;

import com.example.ShopNow.Models.Review;
import com.example.ShopNow.Repositories.DiscountRepository;
import com.example.ShopNow.Services.WebService;
import com.example.ShopNow.Models.dao.CategoryDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    WebService webService;
    CategoryDAO categoryDAO;
    @Autowired
    public  AdminController(WebService webService,CategoryDAO categoryDAO){
        this.webService=webService;
        this.categoryDAO=categoryDAO;

    }

    @PostMapping("/startSaling")
    public ResponseEntity<?> startSaling(@RequestParam("nbOfDays") int nbDays,
                                         @RequestParam("productIds") List<Integer> productIds,@RequestParam("saleRate")double saleRate){
        return webService.startSale(nbDays,productIds,saleRate);
    }
    @DeleteMapping("/stopSaling")
    public ResponseEntity<?> stopSaling(@RequestParam("saleId") int saleId){
        return webService.stopSale(saleId);
    }

    @PostMapping("/generateDisCodes")
    public ResponseEntity<?> createDisCodes(@RequestParam("codesAmount") int codeAmount,@RequestParam("discountAmount") float discountAmount){
        return webService.createDisCodes(codeAmount,discountAmount);
    }
    @GetMapping("/subs")
    public ResponseEntity<?> getAllSubCats() throws IOException {
        return ResponseEntity.ok(Map.of(
                "status","success",
                "prod",categoryDAO.loadAllSubcategories()

        ));
    }

}

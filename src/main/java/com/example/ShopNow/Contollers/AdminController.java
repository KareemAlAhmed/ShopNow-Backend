package com.example.ShopNow.Contollers;

import com.example.ShopNow.Models.Review;
import com.example.ShopNow.Repositories.DiscountRepository;
import com.example.ShopNow.Services.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    WebService webService;

    @Autowired
    public  AdminController(WebService webService){
        this.webService=webService;
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

}

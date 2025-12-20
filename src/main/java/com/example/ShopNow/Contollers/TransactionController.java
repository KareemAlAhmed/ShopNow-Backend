package com.example.ShopNow.Contollers;

import com.example.ShopNow.Models.Review;
import com.example.ShopNow.Services.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    WebService webService;

    @Autowired
    public  TransactionController(WebService webService){
        this.webService=webService;
    }

    @GetMapping("/discountCode")
    public ResponseEntity<?> getDiscount(@RequestParam("code") String code){
        return webService.getDiscountCode(code);
    }
}

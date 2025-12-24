package com.example.ShopNow.Services;

import com.example.ShopNow.Exceptions.DiscountErrorException;
import com.example.ShopNow.Exceptions.InsufficientFundsException;
import com.example.ShopNow.Exceptions.ProductNotFoundException;
import com.example.ShopNow.Exceptions.UserNotFoundException;
import com.example.ShopNow.Models.*;
import com.example.ShopNow.Models.dao.CategoryDAO;
import com.example.ShopNow.Models.dao.ProductDAO;
import com.example.ShopNow.Models.dao.UserDAO;
import com.example.ShopNow.Repositories.DiscountRepository;
import com.example.ShopNow.Repositories.ReviewRepository;
import com.example.ShopNow.Repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.example.ShopNow.Repositories.PurchaseRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
@Service
public class WebService {
    public ProductDAO productDAO;
    public CategoryDAO categoryDAO;
    public UserDAO userDAO;
    public PurchaseRepository purchaseRepo;
    public ReviewRepository reviewRepository;
    public SaleRepository saleRepository;
    DiscountRepository discountRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public WebService(ProductDAO productDAO,CategoryDAO categoryDAO,UserDAO userDAO,PurchaseRepository purchaseRepo,ReviewRepository reviewRepository,SaleRepository saleRepository, DiscountRepository discountRepository){
        this.productDAO=productDAO;
        this.categoryDAO=categoryDAO;
        this.userDAO=userDAO;
        this.purchaseRepo=purchaseRepo;
        this.reviewRepository=reviewRepository;
        this.saleRepository=saleRepository;
        this.discountRepository=discountRepository;
    }
    //===========Product==========================

    public void loadProductsData() throws IOException {
        categoryDAO.loadAllCat();
        productDAO.loadAllProducts();
        categoryDAO.loadAllSubcategories();
    }
    public ResponseEntity<?> getProductById(int id) throws ProductNotFoundException {
        Product prod=productDAO.getProdById(id);
        if (prod == null){
            throw new ProductNotFoundException("Product Not Found!");
        }else{
            List<Product> relatedProd=productDAO.getRelatedProd(prod.getSubcategoryName());
            return ResponseEntity.ok(Map.of(
                    "status","success",
                    "prod",prod,
                    "relatedProds",relatedProd
            ));
        }
    }
    public ResponseEntity<?> getAllProductByCat(String prodType){
        List<Product> prods=productDAO.getAllProductByCat(prodType);
        return ResponseEntity.ok(Map.of(
                "status","success",
                "prods",prods
        ));
    }




    //===========User==========================

    public ResponseEntity<?> save(User user){
        try{
            return userDAO.save(user);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of("error", "Internal server error: " + e.getMessage())
            );
        }
    }
    public ResponseEntity<?> login(User user){
        user.setPassword("{noop}"+user.getPassword());
        try {
            ResponseEntity<?> res = this.getUser("email", user.getEmail());
            if (res.getStatusCode().is2xxSuccessful()) {
                Object body = res.getBody();
                // Since you returned Map.of(), the body will be a Map

                Map<String, Object> responseMap = (Map<String, Object>) body;

                String status = (String) responseMap.get("status");
                User founduser = (User)responseMap.get("user");

                if(founduser.getPassword().equals(user.getPassword())){
                    return ResponseEntity.ok(Map.of(
                            "status", "success",
                            "user",founduser));
                }else{
                    return ResponseEntity.badRequest().body(Map.of("error", Map.of("password","Incorrect Password!")));
                }

            }else{
                return ResponseEntity.badRequest().body(Map.of("error", Map.of("email","Email Doesnt Exist!")));
            }
        }catch (UserNotFoundException exc){
            return ResponseEntity.badRequest().body(Map.of("error", Map.of("email","Email Doesnt Exist!")));

        }

    }
    public ResponseEntity<?> checkOut(User user, String discountCode ){
        User foundUser=userDAO.getUserById(user.getId());
        if (foundUser == null){
            throw new UserNotFoundException("User Doesnt Exist!");
        }
        List<CartItem> list=user.getCart();
        Map<Integer,Integer> prodsId=new HashMap<>();
        double totalCost=0.0;
        for (int i=0;i<list.size();i++){
            CartItem item=list.get(i);
            if(item.getProdNewPrice() != null){
                totalCost += Double.parseDouble(item.getProdNewPrice()) *  Integer.parseInt(item.getQuantity());
            }else{
                totalCost += Double.parseDouble(item.getProdPrice()) *  Integer.parseInt(item.getQuantity());
            }
            if(!productDAO.updateProdQt(item.getId(),Integer.parseInt(item.getQuantity()))){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                        Map.of("error", "Problem While Updating the quantity of ProdId "+item.getId())
                );
            }
            prodsId.put(item.getId(),Integer.parseInt(item.getQuantity()));
        }
        double finalCost=Math.round(totalCost + totalCost*0.7) ;
        Discount discount=new Discount();
        System.out.println(discountCode);
        if(discountCode == null){
             discount=discountRepository.findByCode(discountCode)
                    .orElseThrow(() -> new DiscountErrorException("Wrong Discount Code!"));
            double discountAmount=finalCost* (double)discount.getDiscountAmount();
            System.out.println("The Original Cost : "+finalCost);
            System.out.println("The Discount Amount : "+discountAmount);
            finalCost -=Math.round(discountAmount);
            System.out.println("The New Cost : "+finalCost);
        }
        if(user.getFunds() < finalCost){
            throw new InsufficientFundsException("Insufficient Funds!");
        }
        Purchase purchase=new Purchase();
        purchase.setPrice(finalCost);
        purchase.setProds(list);
        purchase.setUser(user);
        if(discountCode != null){
            discountRepository.deleteByCode(discountCode);
        }

        purchase.setTimestamps(LocalDateTime.now());


        purchaseRepo.save(purchase);
        double newFunds=user.getFunds() - finalCost;
        user.setFunds(newFunds);
        user.setCart(new ArrayList<>());
        User newUser=userDAO.updateUser(user);

        return ResponseEntity.ok(Map.of(
                "status", "success",
                "user",newUser,
                    "updatedProds",prodsId));
    }
    public ResponseEntity<?> updateCartWish(User data){
        userDAO.updateCartWish(data);
        return ResponseEntity.ok(Map.of(
                "status", "success"));
    }
    public List<User> getAllUser(){
        return userDAO.getAllUser();
    }
    public ResponseEntity<?> getUser(String userField, Object data){
        if(userField.equals("email")){
            try{
                User user=  userDAO.getUserByField("email",data);

                return ResponseEntity.ok(Map.of(
                        "status", "success",
                        "user",user));
            }catch (Exception e){
                throw new UserNotFoundException("User Doesnt Exist!");
            }
        } else if (userField.equals("id")) {
            try{
                User user=  userDAO.getUserByField("id",data);

                return ResponseEntity.ok(Map.of(
                        "status", "success",
                        "user",user));

            }catch (Exception e){
                throw new UserNotFoundException("User Doesnt Exist!");
            }

        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of("error", "Internal server error")
            );
        }
    }
    public ResponseEntity<?> updateUseData(User user,String extaData){
        User foundUser=userDAO.getUserById(user.getId());
        if(!extaData.equals("")){
            user.setPassword("{noop}"+user.getPassword());
            if(foundUser == null){
                throw new UserNotFoundException("User Doesnt Exist!");
            }
            if(!user.getPassword().equals(foundUser.getPassword())){
                return ResponseEntity.badRequest().body(Map.of("error", Map.of("password","Incorrect Password!")));
            }
            user.setPassword("{noop}"+extaData);
            userDAO.updateUser(user);
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "user",user,
                    "updatedField","password"));
        }else{

            if(!user.getEmail().equals(foundUser.getEmail())){
                User catchUser=userDAO.getUserByField("email",user.getEmail());
                if(catchUser != null){
                    return ResponseEntity.badRequest().body(Map.of("error", Map.of("email","Email Already Been Taken!")));
                }
            }
            if(!user.getPhone().equals(foundUser.getPhone())){
                User catchUser=userDAO.getUserByField("phone",user.getPhone());
                if(catchUser != null){
                    return ResponseEntity.badRequest().body(Map.of("error", Map.of("phone","Phone Already Been Use!")));
                }
            }
            if(!user.getUsername().equals(foundUser.getUsername())){
                User catchUser=userDAO.getUserByField("username",user.getUsername());
                if(catchUser == null){
                    String updateSql = "UPDATE authorities SET username = ? WHERE username = ?";
                    jdbcTemplate.update(updateSql, user.getUsername(), foundUser.getUsername());
                    userDAO.updateUserAuthorities(foundUser.getUsername(),"username",user.getUsername());
                }else{
                    return ResponseEntity.badRequest().body(Map.of("error", Map.of("username","Username Already Been Taken!")));
                }
            }
            userDAO.updateUser(user);

            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "user",user,
                    "updatedField","all"));
        }

    }
    public ResponseEntity<?> searchForProds(String search){
        List<Product> prods=productDAO.searchProds(search);
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "prods",prods,
                "recordsFound",prods.size()));
    }

    //=========Review================
    public  double floorToNearestHalf(double number) {
        return Math.floor(number * 2) / 2.0;
    }
    public ResponseEntity<?> createReview(Review review,int userId,int prodId){
        User foundUser=userDAO.getUserById(userId);
        if(foundUser == null){
            throw new UserNotFoundException("User Not Found");
        }
        Product prod=productDAO.getProdById(prodId);
        if(prod == null){
            throw new ProductNotFoundException("Product Not Found");
        }
        review.setUser(foundUser);
        review.setProduct(prod);
        review.setCreatedAt(LocalDateTime.now());


        float prodRevRate=prod.getReviewRates();
        float newRate=0;

        if(prodRevRate == 0){
            newRate=review.getRating();
        }else{
            newRate=(prodRevRate * prod.getReviews().size() + review.getRating()) / (prod.getReviews().size() + 1);
        }


        prod.setReviewRates((float) floorToNearestHalf(newRate));
        productDAO.updateProduct(prod);
        reviewRepository.save(review);

        return ResponseEntity.ok(Map.of(
                "status", "success",
                "review",review,
                "prodNewRate",(float) floorToNearestHalf(newRate)
                ));
    }

    //============Sale===============
    public ResponseEntity<?> startSale(int nbDays,List<Integer> productIds,double saleRate){

        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime dateAfterDays = currentDate.plusDays(nbDays);

        System.out.println("Current Date: " + currentDate);
        System.out.println("Date after 5 days: " + dateAfterDays);
        Product prod;
        for(int i=0;i<productIds.size();i++){
            prod=productDAO.getProdById(productIds.get(i));
            System.out.println("Prod "+ (i +1) + " : "+prod.getName());
            System.out.println("Original Price : "+prod.getPrice());
            double price=Double.parseDouble(prod.getPrice());
            double newPrice=price * saleRate;
            prod.setNewPrice(String.valueOf(newPrice));
            productDAO.updateProduct(prod);
        }
        Sale sale=new Sale();
        sale.setSalePerc((float) saleRate);
        sale.setStartDate(currentDate);
        sale.setEndDate(dateAfterDays);
        sale.setProductIds(productIds);
        saleRepository.save(sale);
        return ResponseEntity.ok(Map.of(
                "status", "success"
        ));
    }

    public ResponseEntity<?> stopSale(int saleId){
        Sale sale=saleRepository.getReferenceById(saleId);
        Product prod;
        for(int i=0;i<sale.getProductIds().size();i++){
            prod=productDAO.getProdById(sale.getProductIds().get(i));
            System.out.println("Prod "+ (i +1) + " : "+prod.getName());
            System.out.println("Original Price : "+prod.getPrice());
            prod.setNewPrice(null);
            productDAO.updateProduct(prod);
        }
        saleRepository.delete(sale);
        return ResponseEntity.ok(Map.of(
                "status", "success"
        ));
    }


    //===========Discount==========
    public String generateFormattedDiscountCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();

        StringBuilder code = new StringBuilder();

        // Generate 4 groups of 4 characters separated by hyphens
        for (int group = 0; group < 4; group++) {
            for (int i = 0; i < 4; i++) {
                int index = random.nextInt(characters.length());
                code.append(characters.charAt(index));
            }
            if (group < 3) {
                code.append("-");
            }
        }

        return code.toString();
    }

    public ResponseEntity<?> createDisCodes(int codeAmount, float discountAmount){
        for(int i=0;i<codeAmount;i++){
            Discount discount=new Discount();
            discount.setCode(generateFormattedDiscountCode());
            discount.setDiscountAmount(discountAmount);
            discount.setCreatedAt(LocalDateTime.now());
            System.out.println(discount.toString());
            discountRepository.save(discount);
        }
        return ResponseEntity.ok(Map.of(
                "status", "success"));
    }

    public ResponseEntity<?> getDiscountCode(String code){
        Discount discount=discountRepository.findByCode(code)
                .orElseThrow(() -> new DiscountErrorException("Wrong Discount Code!"));
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "discount",discount));
    }

    //===========Categories================
    public List<Category> getAllCate(){
        return categoryDAO.getAllCats();
    }
}


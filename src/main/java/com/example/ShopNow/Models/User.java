package com.example.ShopNow.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String username;
    private String email;
    private String password;
    private String phone;
    private int enabled;
    private double funds;

    @Convert(converter = CartItemListConverter.class)
    //@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Column(columnDefinition = "TEXT")
    private List<CartItem> cart = new ArrayList<>();

    @Convert(converter = CartItemListConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<CartItem> wishlist = new ArrayList<>();


    @OneToMany(mappedBy = "user") // "parent" refers to the field in ChildEntity
    private List<Purchase> purchases;

    @OneToMany(mappedBy = "user") // "parent" refers to the field in ChildEntity
    private List<Review> reviews;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;



    //Vendor
    private float reviewRates;


    @OneToMany(mappedBy = "sellerUser") // "parent" refers to the field in ChildEntity
    private List<Product> sellingProds;

    @OneToMany(mappedBy = "sellerUser") // "parent" refers to the field in ChildEntity
    private List<Review> prodReviewed;
    @Column(name = "isVendor")
    private int isVendor=0;

    public boolean isVendor() {
        return isVendor != 0 ;
    }

    public void setVendor(boolean vendor) {
        isVendor = vendor ? 1 : 0;
    }

    public float getReviewRates() {
        return reviewRates;
    }

    public void setReviewRates(float reviewRates) {
        this.reviewRates = reviewRates;
    }

    public List<Product> getSellingProds() {
        return sellingProds;
    }

    public void setSellingProds(List<Product> sellingProds) {
        this.sellingProds = sellingProds;
    }

    public List<Review> getProdReviewed() {
        return prodReviewed;
    }

    public void setProdReviewed(List<Review> prodReviewed) {
        this.prodReviewed = prodReviewed;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public List<CartItem> getCart() {
        return cart;
    }
    public  void clearCart(){
        this.cart.clear();
    }
    public void setCart(List<CartItem> cart) {
        this.cart = cart;
    }

    public List<CartItem> getWishlist() {
        return wishlist;
    }

    public void setWishlist(List<CartItem> wishlist) {
        this.wishlist = wishlist;
    }




    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getFunds() {
        return funds;
    }

    public void setFunds(double funds) {
        this.funds = funds;
    }



    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }
}


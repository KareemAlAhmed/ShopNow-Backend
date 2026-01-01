package com.example.ShopNow.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id") // Foreign key column in the Book table
    @JsonIgnoreProperties({"password", "cart", "wishlist", "purchases","reviews","sellingProds","prodReviewed","funds"})
    private User user;

    @ManyToOne
    @JoinColumn(name = "seller_user_id") // Foreign key column in the Book table
    @JsonIgnoreProperties({"password", "cart", "wishlist", "purchases","reviews","sellingProds","prodReviewed","funds"})
    private User sellerUser;

    public User getSellerUser() {
        return sellerUser;
    }

    public void setSellerUser(User sellerUser) {
        this.sellerUser = sellerUser;
    }

    @ManyToOne
    @JoinColumn(name = "product_id") // Foreign key column in the Book table
//    @JsonIgnore
    @JsonIgnoreProperties({"reviews","description","images_url","quantity","Images_url","sellerUser"})
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    private String content;
    private float rating;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

package com.example.ShopNow.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;

import java.io.IOException;
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



    @Convert(converter = CartItemListConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<CartItem> cart = new ArrayList<>();

    @Convert(converter = CartItemListConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<CartItem> wishlist = new ArrayList<>();

    public List<CartItem> getCart() {
        return cart;
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


}


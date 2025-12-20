package com.example.ShopNow.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Purchase {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_id") // Foreign key column in the Book table
    @JsonIgnore
    private User user;



    @Convert(converter = CartItemListConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<CartItem> prods = new ArrayList<>();
    private double price;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamps;
    public LocalDateTime getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(LocalDateTime timestamps) {
        this.timestamps = timestamps;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<CartItem> getProds() {
        return prods;
    }

    public void setProds(List<CartItem> prods) {
        this.prods = prods;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }





}

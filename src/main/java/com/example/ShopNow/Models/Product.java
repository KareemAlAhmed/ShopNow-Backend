package com.example.ShopNow.Models;


import com.example.ShopNow.FlexibleStringListConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @JsonProperty("name")  // Maps JSON "user_name" to Java "username"
    private String name;

    @JsonProperty("description")
    @Lob
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @JsonProperty("price")
    private String price;

    public String getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(String newPrice) {
        this.newPrice = newPrice;
    }

    private  String newPrice;
    @JsonProperty("quantity")
    private String quantity;
    @JsonProperty("Vendor")
    private String Vendor;
    @JsonProperty("ProductType")
    private String ProductType;
    @JsonProperty("categoryName")
    private String categoryName;
    @JsonProperty("subcategoryName")
    private String subcategoryName;
    @JsonProperty("brandname")
    private String brandName;
    @JsonProperty("thumbnail_url")
    @Column(name = "thumbnail_url", length = 1000)
    private String thumbnail_url;
    @Lob
    @Column(name = "Images_url", columnDefinition = "TEXT")
    @Convert(converter = FlexibleStringListConverter.class)
    @JsonProperty("Images_url")
    private List<String> Images_url;


    @OneToMany(mappedBy = "product") // "parent" refers to the field in ChildEntity
    private List<Review> reviews;

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getVendor() {
        return Vendor;
    }

    public void setVendor(String vendor) {
        Vendor = vendor;
    }

    public String getProductType() {
        return ProductType;
    }

    public void setProductType(String productType) {
        ProductType = productType;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public List<String> getImages_url() {
        return Images_url;
    }

    public void setImages_url(List<String> images_url) {
        Images_url = images_url;
    }
    public void setImages_url(String imageUrl) {
        if (imageUrl != null) {
            this.Images_url = List.of(imageUrl);
        }
    }


}

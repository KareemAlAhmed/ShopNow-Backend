package com.example.ShopNow.Models;

import jakarta.persistence.*;

@Embeddable
public class CartItem {

    private int id;
    private String prodName;
    private String prodPrice;

    private String prodNewPrice;
    private String quantity;



    private String prodBrandName;
    private String prodThumbnail;
    private String prodMaxQt;
    public String getProdMaxQt() {
        return prodMaxQt;
    }

    public void setProdMaxQt(String prodMaxQt) {
        this.prodMaxQt = prodMaxQt;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(String prodPrice) {
        this.prodPrice = prodPrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProdThumbnail() {
        return prodThumbnail;
    }

    public void setProdThumbnail(String prodThumbnail) {
        this.prodThumbnail = prodThumbnail;
    }
    public String getProdNewPrice() {
        return prodNewPrice;
    }

    public void setProdNewPrice(String prodNewPrice) {
        this.prodNewPrice = prodNewPrice;
    }
    public String getProdBrandName() {
        return prodBrandName;
    }

    public void setProdBrandName(String prodBrandName) {
        this.prodBrandName = prodBrandName;
    }
}

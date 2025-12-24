package com.example.ShopNow.Models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    public String name;

    @OneToMany(mappedBy = "category") // "parent" refers to the field in ChildEntity
    private List<Subcategory> subCats;

    public List<Subcategory> getSubcategory() {
        return subCats;
    }

    public void setSubcategory(List<Subcategory> subCats) {
        this.subCats = subCats;
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



}

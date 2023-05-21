package com.example.shoppingplatform.Model;

import jakarta.persistence.*;

@Entity
public class Commodity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String name;
    private int price;
    private String classfiation;
    private String attribute;
    @Lob
    @Column(name = "photo", columnDefinition="BLOB")
    private byte[] picture;
    private int quantity;

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setClassfiation(String classfiation) {
        this.classfiation = classfiation;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getClassfiation() {
        return classfiation;
    }

    public String getAttribute() {
        return attribute;
    }

    public byte[] getPicture() {
        return picture;
    }

    public int getQuantity() {
        return quantity;
    }
}

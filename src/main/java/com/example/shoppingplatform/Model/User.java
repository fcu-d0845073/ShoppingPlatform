package com.example.shoppingplatform.Model;

import jakarta.annotation.Generated;
import jakarta.persistence.*;

@Entity
@Table(name = "ShoppingUser")//if you use h2 database, you have to add this line to avoid error. Because the name "User" is reserved word
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String account;
    private String password;

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }
}

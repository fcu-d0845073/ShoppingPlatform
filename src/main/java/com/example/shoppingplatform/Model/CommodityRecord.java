package com.example.shoppingplatform.Model;

import jakarta.persistence.*;

@Entity
public class CommodityRecord {
    @Id
    private int id;
    private String purchaseAccount;
    private String purchaseCommodity;

    public void setPurchaseAccount(String purchaseAccount) {
        this.purchaseAccount = purchaseAccount;
    }

    public void setPurchaseCommodity(String purchaseCommodity) {
        this.purchaseCommodity = purchaseCommodity;
    }

    public int getId() {
        return id;
    }

    public String getPurchaseAccount() {
        return purchaseAccount;
    }

    public String getPurchaseCommodity() {
        return purchaseCommodity;
    }

}

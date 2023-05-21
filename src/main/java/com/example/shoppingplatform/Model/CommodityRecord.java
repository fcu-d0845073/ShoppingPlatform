package com.example.shoppingplatform.Model;

import jakarta.persistence.*;

@Entity
public class CommodityRecord {
    @Id
    private int id;
    private String purchaseAccount;
    @OneToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Commodity purchaseCommodity;
    private boolean used;

    public void setPurchaseAccount(String purchaseAccount) {
        this.purchaseAccount = purchaseAccount;
    }

    public void setPurchaseCommodity(Commodity purchaseCommodity) {
        this.purchaseCommodity = purchaseCommodity;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public int getId() {
        return id;
    }

    public String getPurchaseAccount() {
        return purchaseAccount;
    }

    public Commodity getPurchaseCommodity() {
        return purchaseCommodity;
    }

    public boolean getUsed() {
        return used;
    }
}

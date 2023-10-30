package com.example.electronic_store.Entity;

import java.io.Serializable;

public class Quantity implements Serializable {
    int uid;
    int quantity;

    public Quantity() {
    }

    public Quantity(int uid, int quantity) {
        this.uid = uid;
        this.quantity = quantity;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Quantity{" +
                "uid=" + uid +
                ", quantity=" + quantity +
                '}';
    }
}

package com.example.electronic_store.Entity;

import java.io.Serializable;

public class Cart implements Serializable {
    int cid;
    int uid;
    int pid;
    int quantity;
    int price;

    public Cart() {
    }

    public Cart(int cid, int uid, int pid, int quantity, int price) {
        this.cid = cid;
        this.uid = uid;
        this.pid = pid;
        this.quantity = quantity;
        this.price = price;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


}

package com.example.electronic_store.Entity;

import java.io.Serializable;

public class Orders implements Serializable {
    int order_id;
    int uid;
    int pid;
    int quantity;
    int total_price;

    public Orders() {
    }

    public Orders(int order_id, int uid, int pid, int quantity, int total_price) {
        this.order_id = order_id;
        this.uid = uid;
        this.pid = pid;
        this.quantity = quantity;
        this.total_price = total_price;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
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

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "order_id=" + order_id +
                ", uid=" + uid +
                ", pid=" + pid +
                ", quantity=" + quantity +
                ", total_price=" + total_price +
                '}';
    }
}

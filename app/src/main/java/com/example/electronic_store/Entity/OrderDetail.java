package com.example.electronic_store.Entity;

import java.io.Serializable;

public class OrderDetail implements Serializable {
    String product_name;
    String image;
    int price;
    String orderDate;
    int quantity;
    int uid;
    int pid;
    double totalPrice;

    public OrderDetail() {
    }

    public OrderDetail(String product_name, String image, int price, String orderDate, int quantity, int uid, double totalPrice, int pid) {
        this.product_name = product_name;
        this.image = image;
        this.price = price;
        this.orderDate = orderDate;
        this.quantity = quantity;
        this.uid = uid;
        this.totalPrice = totalPrice;
        this.pid = pid;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "product_name='" + product_name + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", orderDate='" + orderDate + '\'' +
                ", quantity=" + quantity +
                ", uid=" + uid +
                ", pid=" + pid +
                ", totalPrice=" + totalPrice +
                '}';
    }
}

package com.example.electronic_store.Entity;

public class Price {
    double totalPrice;

    public Price() {
    }

    public Price(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Price{" +
                "totalPrice=" + totalPrice +
                '}';
    }
}

package com.example.electronic_store.Entity;

import java.io.Serializable;

public class Product implements Serializable {
    int pid;
    String category;
    int price;
    int quantity;
    String brand;
    String product_name;
    String model_name;
    String manufacture_country;
    String description;
    String image;

    public Product() {
    }

    public Product(int pid, String category, int price, int quantity, String brand, String product_name, String model_name, String manufacture_country, String description, String image) {
        this.pid = pid;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.brand = brand;
        this.product_name = product_name;
        this.model_name = model_name;
        this.manufacture_country = manufacture_country;
        this.description = description;
        this.image = image;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public String getManufacture_country() {
        return manufacture_country;
    }

    public void setManufacture_country(String manufacture_country) {
        this.manufacture_country = manufacture_country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Product{" +
                "pid=" + pid +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", brand='" + brand + '\'' +
                ", product_name='" + product_name + '\'' +
                ", model_name='" + model_name + '\'' +
                ", manufacture_country='" + manufacture_country + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}

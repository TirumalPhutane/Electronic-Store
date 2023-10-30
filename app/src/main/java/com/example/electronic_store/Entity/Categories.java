package com.example.electronic_store.Entity;

import java.io.Serializable;

public class Categories implements Serializable {
    int id;
    String image;
    String category;

    public Categories() {
    }

    public Categories(int id, String image, String category) {
        this.id = id;
        this.image = image;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "View{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}

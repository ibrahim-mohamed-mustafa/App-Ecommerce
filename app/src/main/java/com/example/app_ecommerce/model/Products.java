package com.example.app_ecommerce.model;

public class Products {
    Integer productId;
    String productName;
    String Description;
    Integer Price;
    String Brand;
    Integer imageUrl;

    public Products(Integer productId, String productName, String description, Integer price, String brand, Integer imageUrl) {
        this.productId = productId;
        this.productName = productName;
        Description = description;
        Price = price;
        Brand = brand;
        this.imageUrl = imageUrl;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Integer getPrice() {
        return Price;
    }

    public void setPrice(Integer price) {
        Price = price;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public Integer getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Integer imageUrl) {
        this.imageUrl = imageUrl;
    }
}

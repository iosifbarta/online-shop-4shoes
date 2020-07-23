package org.fastrackit.onlineshop4shoes.transfer;

import javax.validation.constraints.NotNull;

public class SaveProductRequest {

    @NotNull
    private String brandName;

    @NotNull
    private String shoeCode;

    @NotNull
    private double size;

    private String gender;
    private  String description;
    @NotNull
    private double price;
    private String imageUrl;
    @NotNull
    private  int quantity;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getShoeCode() {
        return shoeCode;
    }

    public void setShoeCode(String shoeCode) {
        this.shoeCode = shoeCode;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "SaveProductRequest{" +
                "brandName='" + brandName + '\'' +
                ", shoeCode='" + shoeCode + '\'' +
                ", size=" + size +
                ", gender='" + gender + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}

package com.example.inventorease_project;

public class ProductList {

    private String pname;
    private int quantity;
    private int price;
    private int cost;



    public ProductList(String pname, int quantity, int cost, int price) {
        this.pname = pname;
        this.quantity = quantity;
        this.price = price;
        this.cost =cost;


    }
    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
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

    public int getCost() {return cost;}

    public void setCost(int cost) {this.cost = cost;}
}
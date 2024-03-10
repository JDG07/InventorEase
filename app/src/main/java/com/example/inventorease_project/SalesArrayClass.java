package com.example.inventorease_project;

public class SalesArrayClass {
    private String salesproduct;
    private String salesqty;
    private String salesprice;
    private String salestotal;

    public SalesArrayClass(String salesproduct, String salesqty, String salesprice, String salestotal) {
        this.salesproduct = salesproduct;
        this.salesqty = salesqty;
        this.salesprice = salesprice;
        this.salestotal = salestotal;
    }

    public String getSalesproduct() {
        return salesproduct;
    }

    public void setSalesproduct(String salesproduct) {
        this.salesproduct = salesproduct;
    }

    public String getSalesqty() {
        return salesqty;
    }

    public void setSalesqty(String salesqty) {
        this.salesqty = salesqty;
    }

    public String getSalesprice() {
        return salesprice;
    }

    public void setSalesprice(String salesprice) {
        this.salesprice = salesprice;
    }

    public String getSalestotal() {
        return salestotal;
    }

    public void setSalestotal(String salestotal) {
        this.salestotal = salestotal;
    }
}



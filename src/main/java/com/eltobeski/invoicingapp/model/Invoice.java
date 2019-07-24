package com.eltobeski.invoicingapp.model;

public class Invoice {
    private String employeeID;

    private String numberOfHours;

    private String unitPrice;

    private String cost;

    public Invoice() {
    }

    public Invoice(String employeeID, String numberOfHours, String unitPrice, String cost) {
        this.employeeID = employeeID;
        this.numberOfHours = numberOfHours;
        this.unitPrice = unitPrice;
        this.cost = cost;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getNumberOfHours() {
        return numberOfHours;
    }

    public void setNumberOfHours(String numberOfHours) {
        this.numberOfHours = numberOfHours;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}

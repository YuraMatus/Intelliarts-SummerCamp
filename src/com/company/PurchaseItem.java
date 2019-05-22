package com.company;

class PurchaseItem {
    private double cost;
    private String currency;
    private String productName;

    public PurchaseItem(double cost, String currency, String productName) {
        this.cost = cost;
        this.currency = currency;
        this.productName = productName;
    }

    public double getCost() {
        return cost;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getProductName() {
        return productName;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return getProductName() + " " + getCost() + " " + getCurrency();
    }
}

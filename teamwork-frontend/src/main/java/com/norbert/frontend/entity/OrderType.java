package com.norbert.frontend.entity;

public enum OrderType {
    BODY_WASH(250,"Мийка кузову"),
    BODY_AND_INTERIOR_WASH(350,"Мийка кузову та салону"),
    DRY_CLEANING(1800,"Хімчистка");

    private Integer price;
    private String name;

    OrderType(Integer price, String name) {
        this.price = price;
        this.name = name;
    }


    public Integer getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}

package com.norbert.frontend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Objects;

public class Employee {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("card_number")
    private String cardNumber;

    @JsonProperty("is_working")
    private Boolean working;

    @JsonIgnore
    private final BooleanProperty selected = new SimpleBooleanProperty(false);

    public Employee(Long id, String firstName, String lastName, String email, String cardNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.cardNumber = cardNumber;
    }

    public Employee(String firstName, String lastName, String email, String cardNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.cardNumber = cardNumber;
    }

    public Employee() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public boolean isSelected() {
        return selected.get();
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    public Boolean isWorking() {
        return working;
    }

    public void setWorking(Boolean working) {
        this.working = working;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + "(" + id +")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee employee)) return false;
        return Objects.equals(getId(), employee.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

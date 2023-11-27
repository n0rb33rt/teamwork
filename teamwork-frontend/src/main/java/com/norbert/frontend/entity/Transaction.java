package com.norbert.frontend.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Transaction{
    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    @JsonProperty("employees")
    private List<Employee> employees;
    @JsonProperty("order_type")
    private OrderType orderType;
    @JsonProperty("date")
    private LocalDate date;

    public Transaction(Long id, List<Employee> employees, OrderType orderType, LocalDate localDate) {
        this.id = id;
        this.employees = employees;
        this.orderType = orderType;
        this.date = localDate;
    }

    public Transaction(List<Employee> employees, OrderType orderType, LocalDate localDate) {
        this.employees = employees;
        this.orderType = orderType;
        this.date = localDate;
    }

    public Transaction() {
    }

    public Long getId() {
        return id;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public OrderType getOrderType() {
        return orderType;
    }


    public Integer getPrice() {
        return orderType.getPrice();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", employees=" + employees +
                ", orderType=" + orderType +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
package com.norbert.frontend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;


public class PaySalary {
    @JsonProperty("employee_transactions_map")
    private Map<String, List<Transaction>> employeeTransactionsMap;

    @JsonProperty("employee_salary_map")
    private Map<String ,Double> employeeSalaryMap;

    public PaySalary() {
    }

    public PaySalary(Map<String, List<Transaction>> employeeTransactionsMap, Map<String, Double> employeeSalaryMap) {
        this.employeeTransactionsMap = employeeTransactionsMap;
        this.employeeSalaryMap = employeeSalaryMap;
    }

    public Map<String, List<Transaction>> getEmployeeTransactionsMap() {
        return employeeTransactionsMap;
    }

    public void setEmployeeTransactionsMap(Map<String, List<Transaction>> employeeTransactionsMap) {
        this.employeeTransactionsMap = employeeTransactionsMap;
    }

    public Map<String, Double> getEmployeeSalaryMap() {
        return employeeSalaryMap;
    }

    public void setEmployeeSalaryMap(Map<String, Double> employeeSalaryMap) {
        this.employeeSalaryMap = employeeSalaryMap;
    }
}

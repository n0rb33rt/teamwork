package com.norbert.frontend.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.util.Map;

public class PayrollSalaryStatisticsController {

    @FXML
    private TableView<EmployeeSalaryEntry> tableView;

    private Map<String, Double> employeeSalaryMap;

    public void setEmployeeSalaryMap(Map<String, Double> employeeSalaryMap) {
        this.employeeSalaryMap = employeeSalaryMap;
        updateTableView();
    }

    private void updateTableView() {
        tableView.getItems().clear();

        for (Map.Entry<String, Double> entry : employeeSalaryMap.entrySet()) {
            tableView.getItems().add(new EmployeeSalaryEntry(entry.getKey(), entry.getValue()));
        }
    }

    public static class EmployeeSalaryEntry {
        private final String employee;
        private final Double salary;

        public EmployeeSalaryEntry(String employeeName, Double salary) {
            this.employee = employeeName;
            this.salary = salary;
        }

        public String getEmployee() {
            return employee;
        }

        public Double getSalary() {
            return salary;
        }
    }
}

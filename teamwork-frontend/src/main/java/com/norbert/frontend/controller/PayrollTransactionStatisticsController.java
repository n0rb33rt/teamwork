package com.norbert.frontend.controller;


import com.norbert.frontend.entity.Employee;
import com.norbert.frontend.entity.OrderType;
import com.norbert.frontend.entity.Transaction;
import javafx.fxml.FXML;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PayrollTransactionStatisticsController {

    @FXML
    private TableView<TransactionEntry> tableView;
    @FXML
    private TableColumn<TransactionEntry, Long> id;
    @FXML
    private TableColumn<TransactionEntry, String> employee;
    @FXML
    private TableColumn<TransactionEntry, List<Employee>> employees;
    @FXML
    private TableColumn<TransactionEntry, String> orderType;
    @FXML
    private TableColumn<TransactionEntry, Integer> price;
    @FXML
    private TableColumn<TransactionEntry, Date> date;

    private Map<String, List<Transaction>> employeeTransactionsMap;

    public void setEmployeeTransactionsMap(Map<String, List<Transaction>> employeeTransactionsMap) {
        this.employeeTransactionsMap = employeeTransactionsMap;
        updateTableView();
    }

    private void updateTableView() {
        tableView.getItems().clear();

        for (Map.Entry<String, List<Transaction>> entry : employeeTransactionsMap.entrySet()) {
            for (Transaction transaction : entry.getValue()) {
                tableView.getItems().add(new TransactionEntry(
                        transaction.getId(),
                        entry.getKey(),
                        transaction.getEmployees(),
                        transaction.getOrderType(),
                        transaction.getPrice(),
                        transaction.getDate()
                ));
            }
        }
    }

    public static final class TransactionEntry {
        private final Long id;
        private final String employee;
        private final List<Employee> employees;
        private final OrderType orderType;
        private final Integer price;
        private final LocalDateTime date;

        public TransactionEntry(
                Long id,
                String employee,
                List<Employee> employees,
                OrderType orderType,
                Integer price,
                LocalDateTime date) {
            this.id = id;
            this.employee = employee;
            this.employees = employees;
            this.orderType = orderType;
            this.price = price;
            this.date = date;
        }

        public Long getId() {
            return id;
        }

        public String getEmployee() {
            return employee;
        }

        public List<Employee> getEmployees() {
            return employees;
        }

        public OrderType getOrderType() {
            return orderType;
        }

        public Integer getPrice() {
            return price;
        }

        public LocalDateTime getDate() {
            return date;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (TransactionEntry) obj;
            return Objects.equals(this.id, that.id) &&
                    Objects.equals(this.employee, that.employee) &&
                    Objects.equals(this.employees, that.employees) &&
                    Objects.equals(this.orderType, that.orderType) &&
                    Objects.equals(this.price, that.price) &&
                    Objects.equals(this.date, that.date);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, employee, employees, orderType, price, date);
        }

        @Override
        public String toString() {
            return "TransactionEntry[" +
                    "id=" + id + ", " +
                    "employee=" + employee + ", " +
                    "employees=" + employees + ", " +
                    "orderType=" + orderType + ", " +
                    "price=" + price + ", " +
                    "date=" + date + ']';
        }

        }
}

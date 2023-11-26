package com.norbert.frontend.controller;


import com.norbert.frontend.Application;
import com.norbert.frontend.dialog.AlertDialog;
import com.norbert.frontend.entity.Employee;
import com.norbert.frontend.entity.OrderType;
import com.norbert.frontend.entity.PaySalary;
import com.norbert.frontend.entity.Transaction;
import com.norbert.frontend.exception.ApiServiceException;
import com.norbert.frontend.service.ApiService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.List;
import java.util.Objects;


public class TransactionController {
    private final static Image icon = new Image(Objects.requireNonNull(Application.class.getResourceAsStream("icon.png")));

    @FXML
    private TableView<Transaction> tableView;


    public void initialize() {
        handleRefreshButtonAction();
    }

    @FXML
    private void handleEditAction() {
        Transaction selectedTransaction = tableView.getSelectionModel().getSelectedItem();
        //todo:
    }

    @FXML
    private void handleDeleteAction() {
        Transaction selectedTransaction = tableView.getSelectionModel().getSelectedItem();
        try {
            ApiService.deleteTransaction(selectedTransaction.getId());
            handleRefreshButtonAction();
            AlertDialog.showSuccessDialog("Success","Successfully deleted transaction with id " + selectedTransaction.getId());
        } catch (ApiServiceException e) {
            AlertDialog.showErrorDialog("API Service Error", "There was an error with the API service: " + e.getMessage());
        }
    }

    @FXML
    private void handleAddTransactionButtonAction() {

    }

    @FXML
    private void handleEmployeesButtonAction() throws IOException {
        FXMLLoader salaryStatisticsLoader = new FXMLLoader(Application.class.getResource("EmployeeView.fxml"));
        Stage stage = new Stage();
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Employees");
        GridPane root = salaryStatisticsLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    private void handlePaySalaryButtonAction() {
        try {
            PaySalary paySalary = ApiService.paySalary();
            AlertDialog.showSuccessDialog("Success","Successfully paid salary");
            showSalaryStatistics(paySalary);
            showTransactionStatistics(paySalary);
        } catch (IOException e) {
            AlertDialog.showErrorDialog("Error loading FXML file", "There was an error loading the FXML file.");
        } catch (ApiServiceException e) {
            AlertDialog.showErrorDialog("API Service Error", "There was an error with the API service: " + e.getMessage());
        }
    }

    private void showSalaryStatistics(PaySalary paySalary) throws IOException {
        FXMLLoader salaryStatisticsLoader = new FXMLLoader(Application.class.getResource("PayrollSalaryStatistics.fxml"));
        Stage stage = new Stage();
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Payroll Salary Statistics");
        GridPane root = salaryStatisticsLoader.load();
        PayrollSalaryStatisticsController controller = salaryStatisticsLoader.getController();
        controller.setEmployeeSalaryMap(paySalary.getEmployeeSalaryMap());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void showTransactionStatistics(PaySalary paySalary) throws IOException {
        FXMLLoader transactionStatisticsLoader = new FXMLLoader(Application.class.getResource("PayrollTransactionStatistics.fxml"));
        Stage stage = new Stage();
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Payroll Transaction Statistics");
        GridPane root = transactionStatisticsLoader.load();
        PayrollTransactionStatisticsController controller = transactionStatisticsLoader.getController();
        controller.setEmployeeTransactionsMap(paySalary.getEmployeeTransactionsMap());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.showAndWait();
    }


    @FXML
    private void handleRefreshButtonAction() {
        try {
            tableView.setItems(FXCollections.observableList(ApiService.getAllTransactions()));
        } catch (IOException e) {
            AlertDialog.showErrorDialog("Client error", e.getMessage());
        } catch (ApiServiceException e) {
            AlertDialog.showErrorDialog("API Service Error", "There was an error with the API service: " + e.getMessage());
        }
    }
}
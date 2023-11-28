package com.norbert.frontend.controller;

import com.norbert.frontend.Application;
import com.norbert.frontend.dialog.AlertDialog;
import com.norbert.frontend.entity.PaySalary;
import com.norbert.frontend.entity.Transaction;
import com.norbert.frontend.exception.ApiServiceException;
import com.norbert.frontend.service.ApiService;
import com.norbert.frontend.service.IconUtil;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;


public class TransactionController {

    @FXML
    private TableView<Transaction> tableView;


    public void initialize() {
        handleRefreshButtonAction();
    }
    @FXML
    private void handleAddTransactionButtonAction() {
        openTransactionDialog("Adding a Transaction");
    }

    private void openTransactionDialog(String title) {
        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("TransactionDialog.fxml"));
            GridPane root = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(new Scene(root));
            dialogStage.getIcons().add(IconUtil.MAIN_ICON);
            dialogStage.setTitle(title);
            dialogStage.setResizable(false);
            TransactionDialogController controller = loader.getController();
            controller.setStage(dialogStage);
            dialogStage.showAndWait();
            handleRefreshButtonAction();
        } catch (IOException e) {
            AlertDialog.showErrorDialog("Error",e.getMessage());
        }
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
    private void handleEmployeesButtonAction() throws IOException {
        FXMLLoader salaryStatisticsLoader = new FXMLLoader(Application.class.getResource("EmployeeView.fxml"));
        Stage stage = new Stage();
        stage.getIcons().add(IconUtil.MAIN_ICON);
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
        Alert loadingAlert = createLoadingAlert();

        Task<PaySalary> task = createPaySalaryTask(loadingAlert);

        configureTaskHandlers(task, loadingAlert);

        loadingAlert.show();
        new Thread(task).start();
    }

    private Alert createLoadingAlert() {
        Alert loadingAlert = new Alert(Alert.AlertType.NONE, "Loading...", ButtonType.CLOSE);
        loadingAlert.initStyle(StageStyle.UNDECORATED);
        loadingAlert.initModality(Modality.APPLICATION_MODAL);
        return loadingAlert;
    }

    private Task<PaySalary> createPaySalaryTask(Alert loadingAlert) {
        return new Task<>() {
            @Override
            protected PaySalary call() throws Exception {
                return ApiService.paySalary();
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                loadingAlert.setResult(ButtonType.OK);
                loadingAlert.close();
                handlePaySalarySuccess(getValue());
            }

            @Override
            protected void failed() {
                super.failed();
                loadingAlert.setResult(ButtonType.OK);
                loadingAlert.close();
                handlePaySalaryFailure(getException());
            }
        };
    }

    private void configureTaskHandlers(Task<PaySalary> task, Alert loadingAlert) {
        task.setOnSucceeded(event -> {
            loadingAlert.setResult(ButtonType.OK);
            loadingAlert.close();
            handlePaySalarySuccess(task.getValue());
        });

        task.setOnFailed(event -> {
            loadingAlert.setResult(ButtonType.OK);
            loadingAlert.close();
            handlePaySalaryFailure(task.getException());
        });
    }

    private void handlePaySalarySuccess(PaySalary paySalary) {
        AlertDialog.showSuccessDialog("Success", "Successfully paid salary");
        try {
            showSalaryStatistics(paySalary);
            showTransactionStatistics(paySalary);
        } catch (IOException e) {
            handleException(e, "Error loading FXML file");
        }
    }

    private void handlePaySalaryFailure(Throwable exception) {
        if (exception instanceof IOException) {
            handleException((IOException) exception, "Error loading FXML file");
        } else if (exception instanceof ApiServiceException) {
            handleException((ApiServiceException) exception, "API Service Error");
        }
    }

    private void handleException(IOException e, String title) {
        AlertDialog.showErrorDialog(title, "There was an error: " + e.getMessage());
    }

    private void handleException(ApiServiceException e, String title) {
        AlertDialog.showErrorDialog(title, "There was an error with the API service: " + e.getMessage());
    }


    private void showSalaryStatistics(PaySalary paySalary) throws IOException {
        FXMLLoader salaryStatisticsLoader = new FXMLLoader(Application.class.getResource("PayrollSalaryStatistics.fxml"));
        Stage stage = new Stage();
        stage.getIcons().add(IconUtil.MAIN_ICON);
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
        stage.getIcons().add(IconUtil.MAIN_ICON);
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
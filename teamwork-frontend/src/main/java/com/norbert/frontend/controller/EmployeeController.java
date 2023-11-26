package com.norbert.frontend.controller;


import com.norbert.frontend.dialog.AlertDialog;
import com.norbert.frontend.entity.Employee;
import com.norbert.frontend.exception.ApiServiceException;
import com.norbert.frontend.service.ApiService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;



public class EmployeeController {
    @FXML
    private TableView<Employee> tableView;


    public void initialize() {
        handleRefreshButtonAction();
    }

    @FXML
    private void handleEditAction() {
        //Transaction selectedTransaction = tableView.getSelectionModel().getSelectedItem();

    }

    @FXML
    private void handleToggleWorkingStatusAction() {
        Employee selectedEmployee = tableView.getSelectionModel().getSelectedItem();
        try {
            ApiService.toggleEmployeeWorkingStatus(selectedEmployee.getId());
            handleRefreshButtonAction();
            AlertDialog.showSuccessDialog("Success","Successfully hired/dismiss employee");
        } catch (ApiServiceException e) {
            AlertDialog.showErrorDialog("API Service Error", "There was an error with the API service: " + e.getMessage());
        }
    }



    @FXML
    private void handleAddEmployeeButtonAction() {

    }


    @FXML
    private void handleRefreshButtonAction() {
        try {
            tableView.setItems(FXCollections.observableList(ApiService.getAllEmployees()));
            tableView.refresh();
        } catch (IOException e) {
            AlertDialog.showErrorDialog("Error loading FXML file", "There was an error loading the FXML file.");
        } catch (ApiServiceException e) {
            AlertDialog.showErrorDialog("API Service Error", "There was an error with the API service: " + e.getMessage());
        }
    }

}

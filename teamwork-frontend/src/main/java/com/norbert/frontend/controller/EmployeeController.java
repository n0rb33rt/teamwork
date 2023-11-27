package com.norbert.frontend.controller;

import com.norbert.frontend.Application;
import com.norbert.frontend.dialog.AlertDialog;
import com.norbert.frontend.entity.Employee;
import com.norbert.frontend.exception.ApiServiceException;
import com.norbert.frontend.service.ApiService;
import com.norbert.frontend.service.IconUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployeeController {
    @FXML
    private TableView<Employee> tableView;


    public void initialize() {
        handleRefreshButtonAction();
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
        openEmployeeDialog("Adding an Employee", null);
    }

    @FXML
    private void handleEditAction() {
        Employee selectedEmployee = tableView.getSelectionModel().getSelectedItem();
        openEmployeeDialog("Editing an employee",selectedEmployee);
    }

    private void openEmployeeDialog(String title,Employee employee) {
        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("EmployeeDialog.fxml"));
            GridPane root = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(new Scene(root));
            dialogStage.getIcons().add(IconUtil.MAIN_ICON);
            dialogStage.setTitle(title);
            dialogStage.setResizable(false);
            EmployeeDialogController controller = loader.getController();
            controller.setStage(dialogStage);
            controller.setEmployee(employee);
            dialogStage.showAndWait();
            handleRefreshButtonAction();
        } catch (IOException e) {
            AlertDialog.showErrorDialog("Error",e.getMessage());
        }
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

package com.norbert.frontend.controller;

import com.norbert.frontend.dialog.AlertDialog;
import com.norbert.frontend.entity.Employee;
import com.norbert.frontend.exception.ApiServiceException;
import com.norbert.frontend.service.ApiService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployeeDialogController {

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField cardNumberTextField;

    @FXML
    private Button addButton;

    private Stage stage;

    private Employee selectedEmployee;

    public void setEmployee(Employee employee){
        this.selectedEmployee = employee;
        System.out.println(employee);
        if(employee != null){
            addButton.setText("Edit");
            firstNameTextField.setText(selectedEmployee.getFirstName());
            lastNameTextField.setText(selectedEmployee.getLastName());
            emailTextField.setText(selectedEmployee.getEmail());
            cardNumberTextField.setText(selectedEmployee.getCardNumber());
        }
    }

    @FXML
    private void initialize() {
        firstNameTextField.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        lastNameTextField.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        emailTextField.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        cardNumberTextField.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleAddButtonClick() {
        try {
            if(this.selectedEmployee == null) {
                Employee employee = new Employee(firstNameTextField.getText(), lastNameTextField.getText(), emailTextField.getText(), cardNumberTextField.getText());
                ApiService.saveEmployee(employee);
                AlertDialog.showSuccessDialog("Success", "Successfully added the employee");
            }else {
                Employee employee = new Employee(selectedEmployee.getId(),firstNameTextField.getText(), lastNameTextField.getText(), emailTextField.getText(), cardNumberTextField.getText());
                ApiService.updateEmployee(employee);
                AlertDialog.showSuccessDialog("Success", "Successfully updated the employee");
            }
            stage.close();
        } catch (IOException | ApiServiceException e) {
            AlertDialog.showErrorDialog("Error", e.getMessage());
        }
    }

    @FXML
    private void handleCancelButton() {
        stage.close();
    }

    private void validateFields() {
        boolean isFirstNameValid = validateNameField(firstNameTextField.getText());
        boolean isLastNameValid = validateNameField(lastNameTextField.getText());
        boolean isCardNumberValid = validateCardNumberField(cardNumberTextField.getText());
        boolean isEmailValid = validateEmailField(emailTextField.getText());
        addButton.setDisable(!isFirstNameValid || !isLastNameValid || !isCardNumberValid || !isEmailValid);
    }

    private boolean validateNameField(String name) {
        return name.matches("[A-Z][a-zA-Z]{1,29}");
    }

    private boolean validateEmailField(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    private boolean validateCardNumberField(String cardNumber) {
        return cardNumber.matches("\\d{16}");
    }
}

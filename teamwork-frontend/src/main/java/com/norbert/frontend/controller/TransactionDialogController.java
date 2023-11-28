package com.norbert.frontend.controller;

import com.norbert.frontend.dialog.AlertDialog;
import com.norbert.frontend.entity.Employee;
import com.norbert.frontend.entity.OrderType;
import com.norbert.frontend.entity.Transaction;
import com.norbert.frontend.exception.ApiServiceException;
import com.norbert.frontend.service.ApiService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionDialogController {

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button addButton;

    @FXML
    private ComboBox<OrderType> orderTypeComboBox;

    @FXML
    private ListView<Employee> workersView;

    private Stage stage;

    public void setStage(Stage stage){
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        initOrderTypeComboBox();
        initWorkersView();
        initListeners();
    }

    private void initOrderTypeComboBox() {
        OrderType[] orderTypesArray = OrderType.values();
        List<OrderType> orderTypeList = Arrays.asList(orderTypesArray);
        orderTypeComboBox.setItems(FXCollections.observableArrayList(orderTypeList));
    }

    private void initWorkersView() {
        workersView.setCellFactory(CheckBoxListCell.forListView(Employee::selectedProperty));
        workersView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        addButton.setDisable(true);
        try {
            ObservableList<Employee> employees = FXCollections.observableList(ApiService.getAllEmployees().stream().filter(Employee::isWorking).collect(Collectors.toList()));
            employees.forEach(employee -> employee.selectedProperty().addListener((obs, oldSelected, newSelected) -> handleChanges()));
            workersView.setItems(employees);
        } catch (IOException | ApiServiceException e) {
            AlertDialog.showErrorDialog("Error", e.getMessage());
        }
    }

    private void initListeners() {
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> handleChanges());
        orderTypeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> handleChanges());
    }

    @FXML
    private void handleAddButtonClick() {
        LocalDate selectedDate = datePicker.getValue();
        OrderType selectedOrderType = orderTypeComboBox.getValue();
        List<Employee> selectedEmployees = workersView.getItems().stream().filter(Employee::isSelected).collect(Collectors.toList());
        Transaction transaction = new Transaction(selectedEmployees, selectedOrderType, selectedDate);
        try {
            ApiService.saveTransaction(transaction);
            AlertDialog.showSuccessDialog("Success", "Successfully added the transaction");
            stage.close();
        } catch (IOException | ApiServiceException e) {
            AlertDialog.showErrorDialog("Error", e.getMessage());
        }
    }

    @FXML
    private void handleCancelButton(){
        stage.close();
    }

    private void handleChanges() {
        long selectedCount = workersView.getItems().stream().filter(Employee::isSelected).count();
        boolean employeesAreNotValid = selectedCount > 3 || selectedCount == 0;
        addButton.setDisable(datePicker.getValue() == null || orderTypeComboBox.getValue() == null || employeesAreNotValid);
    }
}

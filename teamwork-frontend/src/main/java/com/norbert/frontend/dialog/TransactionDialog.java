package com.norbert.frontend.dialog;

import com.norbert.frontend.entity.Employee;
import com.norbert.frontend.entity.OrderType;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TransactionDialog extends Dialog<Pair<OrderType, List<Employee>>> {

    private ComboBox<OrderType> orderTypeComboBox;
    private ComboBox<Employee> employeeComboBox;
    private DatePicker datePicker;

    public TransactionDialog(List<Employee> allEmployees) {
        this.setTitle("Adding Transaction");
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/norbert/frontend/icon.png")));


        Stage stage = (Stage) getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);

        // Set the button types.
        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        // Create the order type ComboBox
        orderTypeComboBox = new ComboBox<>(FXCollections.observableArrayList(OrderType.values()));
        orderTypeComboBox.setPromptText("Select Order Type");

        // Create the ComboBox for selecting employees with checkboxes
        employeeComboBox = new ComboBox<>(FXCollections.observableArrayList(allEmployees));
        employeeComboBox.setCellFactory(param -> new CheckBoxListCell<>(Employee::selectedProperty));
        employeeComboBox.setButtonCell(new ListCell<Employee>() {
            @Override
            protected void updateItem(Employee item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString()); // Set the text to display in the ComboBox
                }
            }
        });

        // Create the DatePicker for selecting the date
        datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());

        // Set up the GridPane layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Order Type:"), 0, 0);
        grid.add(orderTypeComboBox, 1, 0);

        grid.add(new Label("Employees:"), 0, 1);
        grid.add(employeeComboBox, 1, 1);

        grid.add(new Label("Date:"), 0, 2);
        grid.add(datePicker, 1, 2);

        // Enable/Disable Add button depending on whether all fields are filled
        Node addButtonNode = this.getDialogPane().lookupButton(addButton);
        addButtonNode.setDisable(true);

        // Add listener to validate the input
        orderTypeComboBox.valueProperty().addListener((observable, oldValue, newValue) ->
                addButtonNode.setDisable(newValue == null || employeeComboBox.getValue() == null));

        employeeComboBox.valueProperty().addListener((observable, oldValue, newValue) ->
                addButtonNode.setDisable(orderTypeComboBox.getValue() == null || newValue == null));

        // Add event filter to handle mouse clicks on the background
        getDialogPane().addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            Node source = event.getPickResult().getIntersectedNode();
            if (!(source instanceof CheckBox || source instanceof ComboBox || source instanceof Button)) {
                // Clicking on the background, close the dialog only if not clicking on CheckBox, ComboBox, or Button
                event.consume();
            }
        });

// Set the setOnCloseRequest property to null
        ((Stage) getDialogPane().getScene().getWindow()).setOnCloseRequest(null);


        // Set the GridPane layout as the content of the dialog
        this.getDialogPane().setContent(grid);

        // Request focus on the orderTypeComboBox by default
        Platform.runLater(orderTypeComboBox::requestFocus);

        // Convert the result to a pair (order type, list of selected employees) when the add button is clicked
        this.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                OrderType selectedOrderType = orderTypeComboBox.getValue();
                List<Employee> selectedEmployees = employeeComboBox.getItems().stream()
                        .filter(Employee::isSelected)
                        .collect(Collectors.toList());
                return new Pair<>(selectedOrderType, selectedEmployees);
            }
            return null;
        });
        // Add event filter to handle mouse clicks on the background
        getDialogPane().addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            Node source = event.getPickResult().getIntersectedNode();
            if (source instanceof CheckBox) {
                // Clicking on the CheckBox, handle the selection
                CheckBox checkBox = (CheckBox) source;
                Employee employee = (Employee) checkBox.getUserData();
                employee.setSelected(!employee.isSelected()); // Toggle the selection
            } else if (!(source instanceof ComboBox || source instanceof Button)) {
                // Clicking on the background or outside the ComboBox, close the dialog
                close();
            }
        });

    }
}

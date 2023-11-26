package com.norbert.frontend.dialog;

import com.norbert.frontend.Application;
import com.norbert.frontend.entity.Employee;
import com.norbert.frontend.entity.OrderType;
import com.norbert.frontend.entity.Transaction;
import com.norbert.frontend.exception.ApiServiceException;
import com.norbert.frontend.service.ApiService;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class AddingTransactionDialog extends Dialog<Pair<OrderType, List<Employee>>> {
    private static final Image ICON = new Image(Objects.requireNonNull(Application.class.getResourceAsStream("icon.png")));
    private static final String TITLE = "Adding Transaction";
    private final ComboBox<OrderType> orderTypeComboBox;
    private ListView<Employee> employeesView;

    public AddingTransactionDialog(List<Employee> allEmployees) {
        initializeDialogPane();

        orderTypeComboBox = createComboBox(OrderType.values(), "Select Order Type");

        GridPane grid = setupGridPane(allEmployees);

        addButtonEventHandler();

        // Set the GridPane layout as the content of the dialog
        this.getDialogPane().setContent(grid);

        // Request focus on the orderTypeComboBox by default
        Platform.runLater(orderTypeComboBox::requestFocus);
    }

    private void initializeDialogPane() {
        this.setTitle(TITLE);
        Stage stage = (Stage) getDialogPane().getScene().getWindow();
        stage.getIcons().add(ICON);

        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        getDialogPane().addEventFilter(MouseEvent.MOUSE_CLICKED, this::handleMouseClick);
        ((Stage) getDialogPane().getScene().getWindow()).setOnCloseRequest(null);
    }

    private GridPane setupGridPane(List<Employee> allEmployees) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Order Type:"), 0, 0);
        grid.add(orderTypeComboBox, 1, 0);

        employeesView = createEmployeeListView(allEmployees);
        grid.add(new Label("Employees:"), 0, 1);
        grid.add(employeesView, 1, 1);

        return grid;
    }

    private void addButtonEventHandler() {
        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        Node addButtonNode = this.getDialogPane().lookupButton(addButton);

        if (addButtonNode != null) {
            addButtonNode.disableProperty().bind(
                    orderTypeComboBox.valueProperty().isNull()
                            .or(employeesView.getSelectionModel().selectedItemProperty().isNull())
                            .or(Bindings.size(employeesView.getSelectionModel().getSelectedItems()).greaterThan(3))
            );

            addButtonNode.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleAddButtonClick);
        } else {
            System.err.println("Add button not found");
        }
    }


    private void handleMouseClick(MouseEvent event) {
        Node source = event.getPickResult().getIntersectedNode();
        if (!(source instanceof CheckBox || source instanceof ComboBox || (source instanceof Button && ((Button) source).getText().equals("Add")))) {
            event.consume();
        }
    }

    private void handleAddButtonClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            performTransactionSave();
        }
    }

    private void performTransactionSave() {
        OrderType selectedOrderType = orderTypeComboBox.getValue();
        List<Employee> selectedEmployees = employeesView.getSelectionModel().getSelectedItems();
        LocalDate localDateTime = LocalDate.now();

        if (selectedOrderType != null && !selectedEmployees.isEmpty() && selectedEmployees.size() <= 3) {
            Transaction transaction = new Transaction(selectedEmployees, selectedOrderType, localDateTime);

            // Use a separate thread for the API call
            new Thread(() -> {
                try {
                    Transaction savedTransaction = ApiService.saveTransaction(transaction);

                    // Update the UI on the JavaFX Application Thread
                    Platform.runLater(() -> {
                        System.out.println(savedTransaction);
                        close();
                    });
                } catch (IOException e) {
                    handleApiServiceException("Error loading FXML file", e);
                } catch (ApiServiceException e) {
                    handleApiServiceException("API Service Error", e);
                }
            }).start();
        }
    }

    private void handleApiServiceException(String title, Exception exception) {
        Platform.runLater(() -> AlertDialog.showErrorDialog(title, exception.getMessage()));
    }

    private <T> ComboBox<T> createComboBox(T[] items, String promptText) {
        ComboBox<T> comboBox = new ComboBox<>(FXCollections.observableArrayList(items));
        comboBox.setPromptText(promptText);
        return comboBox;
    }

    private ListView<Employee> createEmployeeListView(List<Employee> allEmployees) {
        ListView<Employee> listView = new ListView<>(FXCollections.observableArrayList(allEmployees));
        listView.setCellFactory(param -> new ListCell<>() {
            private final CheckBox checkBox = new CheckBox();

            @Override
            protected void updateItem(Employee item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    checkBox.setText(item.toString());
                    checkBox.setSelected(item.isSelected());
                    setGraphic(checkBox);
                }
            }
        });
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        return listView;
    }
}

package com.norbert.frontend.service;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.norbert.frontend.entity.Employee;
import com.norbert.frontend.entity.Transaction;

public class JsonRequestBuilder {
    public static JsonObject buildTransactionJson(Transaction transaction) {
        JsonObject jsonRequest = new JsonObject();
        jsonRequest.addProperty("date", transaction.getDate().toString());
        jsonRequest.addProperty("order_type", transaction.getOrderType().name());
        JsonArray employeesArray = new JsonArray();
        for (Employee employee : transaction.getEmployees()) {
            employeesArray.add(buildEmployeeJson(employee));
        }
        jsonRequest.add("employees", employeesArray);
        return jsonRequest;
    }

    public static JsonObject buildEmployeeJson(Employee employee) {
        JsonObject employeeObject = new JsonObject();
        if (employee.getId() != null)
            employeeObject.addProperty("id", employee.getId());
        employeeObject.addProperty("first_name", employee.getFirstName());
        employeeObject.addProperty("last_name", employee.getLastName());
        employeeObject.addProperty("card_number", employee.getCardNumber());
        employeeObject.addProperty("email", employee.getEmail());
        return employeeObject;
    }
}

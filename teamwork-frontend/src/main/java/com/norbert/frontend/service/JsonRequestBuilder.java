package com.norbert.frontend.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.norbert.frontend.entity.Employee;
import com.norbert.frontend.entity.Transaction;

public class JsonRequestBuilder {
    public static String buildTransactionJson(Transaction transaction) {
        JsonObject jsonRequest = new JsonObject();
        jsonRequest.addProperty("date", transaction.getDate().toString());
        jsonRequest.addProperty("order_type", transaction.getOrderType().name());
        JsonArray employeesArray = new JsonArray();
        for (Employee employee : transaction.getEmployees()) {
            employeesArray.add(buildEmployeeJson(employee));
        }
        jsonRequest.add("employees", employeesArray);
        return new Gson().toJson(jsonRequest);
    }

    public static String buildEmployeeJson(Employee employee) {
        JsonObject jsonRequest = new JsonObject();
        if(employee.getId() != null)
            jsonRequest.addProperty("id",employee.getId());
        jsonRequest.addProperty("first_name", employee.getFirstName());
        jsonRequest.addProperty("last_name", employee.getLastName());
        jsonRequest.addProperty("card_number",employee.getCardNumber());
        jsonRequest.addProperty("email",employee.getEmail());
        return new Gson().toJson(jsonRequest);
    }
}

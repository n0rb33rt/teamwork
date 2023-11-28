package com.norbert.frontend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.norbert.frontend.entity.Employee;
import com.norbert.frontend.entity.PaySalary;
import com.norbert.frontend.entity.Transaction;
import com.norbert.frontend.exception.ApiServiceException;
import okhttp3.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ApiService {
    private final static OkHttpClient client;
    private final static ObjectMapper objectMapper = new ObjectMapper();
    private final static String BASE_URL = "http://localhost:8080/api/v1";

    static {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(60000, TimeUnit.MILLISECONDS);
        builder.connectTimeout(60000, TimeUnit.MILLISECONDS);
        builder.callTimeout(60000, TimeUnit.MILLISECONDS);
        client = builder.build();
        objectMapper.registerModule(new JavaTimeModule());
    }

    private static Response executeRequest(Request request){
        try {
            return client.newCall(request).execute();
        } catch (Exception e) {
            throw new ApiServiceException("Error communicating with the server");
        }
    }

    private static <T> T handleResponse(Response response, TypeReference<T> responseType) throws IOException {
        assert response.body() != null;
        String jsonResponse = response.body().string();

        if (response.isSuccessful()) {
            return objectMapper.readValue(jsonResponse, responseType);
        } else {
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
            String message = jsonNode.get("message").asText();
            throw new ApiServiceException(message);
        }
    }

    private static <T> T handleResponse(Response response, Class<T> responseType) throws IOException {
        assert response.body() != null;
        String jsonResponse = response.body().string();

        if (response.isSuccessful()) {
            return objectMapper.readValue(jsonResponse, responseType);
        } else {
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
            String message = jsonNode.get("message").asText();
            throw new ApiServiceException(message);
        }
    }

    private static <T> T get(String path, TypeReference<T> responseType) throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + path)
                .get()
                .build();

        Response response = executeRequest(request);
        return handleResponse(response, responseType);
    }

    private static <T> T post(String path, String jsonRequest, Class<T> responseType) throws IOException {
        RequestBody requestBody = RequestBody.create(jsonRequest, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(BASE_URL + path)
                .post(requestBody)
                .build();

        Response response = executeRequest(request);
        return handleResponse(response, responseType);
    }


    private static void put(String path, String jsonRequest) {
        RequestBody requestBody = RequestBody.create(jsonRequest, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(BASE_URL + path)
                .put(requestBody)
                .build();

        Response response = executeRequest(request);
        response.close();
        if (!response.isSuccessful()) {
            throw new ApiServiceException(response.message());
        }
    }

    private static void delete(String path){
        Request request = new Request.Builder()
                .url(BASE_URL + path)
                .delete()
                .build();

        Response response = executeRequest(request);
        response.close();
        if (!response.isSuccessful()) {
            throw new ApiServiceException(response.message());
        }
    }


    public static Transaction saveTransaction(Transaction transaction) throws IOException {
        JsonObject jsonObject = JsonRequestBuilder.buildTransactionJson(transaction);
        String jsonRequest =  new Gson().toJson(jsonObject);
        return post("/transaction", jsonRequest, Transaction.class);
    }
    public static List<Transaction> getAllTransactions() throws IOException {
        return get("/transaction", new TypeReference<>() {});
    }

    public static void deleteTransaction(Long id){
        delete("/transaction/" + id);
    }


    public static PaySalary paySalary() throws IOException {
        return post("/salary", "", PaySalary.class);
    }



    public static Employee saveEmployee(Employee employee) throws IOException {
        JsonObject jsonObject = JsonRequestBuilder.buildEmployeeJson(employee);
        String jsonRequest =  new Gson().toJson(jsonObject);
        return post("/employee", jsonRequest, Employee.class);
    }

    public static void toggleEmployeeWorkingStatus(Long id) {
        put("/employee/status/" + id, "");
    }

    public static void updateEmployee(Employee employee) throws IOException {
        JsonObject jsonObject = JsonRequestBuilder.buildEmployeeJson(employee);
        String jsonRequest =  new Gson().toJson(jsonObject);
        put("/employee", jsonRequest);
    }
    public static List<Employee> getAllEmployees() throws IOException {
        return get("/employee", new TypeReference<>() {});
    }
}

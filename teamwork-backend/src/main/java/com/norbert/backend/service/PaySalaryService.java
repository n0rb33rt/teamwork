package com.norbert.backend.service;

import com.norbert.backend.dto.PaySalaryDTO;
import com.norbert.backend.email.BuildingEmailMessageRequest;
import com.norbert.backend.email.EmailBuilder;
import com.norbert.backend.email.EmailSender;
import com.norbert.backend.email.SendingEmailRequest;
import com.norbert.backend.entity.Employee;
import com.norbert.backend.entity.Transaction;
import com.norbert.backend.exception.BadRequestException;
import com.norbert.backend.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class PaySalaryService {
    private final TransactionRepository transactionRepository;
    private final EmailSender emailSender;

    public PaySalaryDTO mockPaySalary() {
        List<Transaction> transactions = getTransactionsByPaidIsFalse();
        if (transactions.isEmpty())
            throw new BadRequestException("Nothing to pay");

        Map<Employee, List<Transaction>> employeeTransactionsMap = createEmployeeTransactionsMap(transactions);
        Map<Employee, Integer> employeeSalaryMap = calculateEmployeeSalaries(employeeTransactionsMap);

        sendSalaryEmails(employeeTransactionsMap, employeeSalaryMap);

        updateTransactionsAsPaid(transactions);

        return PaySalaryDTO.builder()
                .employeeTransactionsMap(employeeTransactionsMap)
                .employeeSalaryMap(employeeSalaryMap)
                .build();
    }

    private Map<Employee, List<Transaction>> createEmployeeTransactionsMap(List<Transaction> transactions) {
        Map<Employee, List<Transaction>> employeeTransactionsMap = new HashMap<>();
        for (Transaction transaction : transactions) {
            List<Employee> employees = transaction.getEmployees();
            for (Employee employee : employees) {
                employeeTransactionsMap.computeIfAbsent(employee, k -> new ArrayList<>());
                employeeTransactionsMap.get(employee).add(transaction);
            }
            transaction.setPaid(true);
        }
        return employeeTransactionsMap;
    }

    private Map<Employee, Integer> calculateEmployeeSalaries(Map<Employee, List<Transaction>> employeeTransactionsMap) {
        Map<Employee, Integer> employeeSalaryMap = new HashMap<>();
        for (Map.Entry<Employee, List<Transaction>> entry : employeeTransactionsMap.entrySet()) {
            Employee employee = entry.getKey();
            List<Transaction> employeeTransactions = entry.getValue();
            int salary = calculateSalary(employeeTransactions);
            employeeSalaryMap.put(employee, salary);
        }
        return employeeSalaryMap;
    }

    private int calculateSalary(List<Transaction> employeeTransactions) {
        int salary = 0;
        for (Transaction transaction : employeeTransactions) {
            salary += transaction.getOrderType().getPrice() / transaction.getEmployees().size();
        }
        return salary / 2; // commission
    }

    private void sendSalaryEmails(Map<Employee, List<Transaction>> employeeTransactionsMap, Map<Employee, Integer> employeeSalaryMap) {
        for (Map.Entry<Employee, List<Transaction>> entry : employeeTransactionsMap.entrySet()) {
            Employee employee = entry.getKey();
            List<Transaction> employeeTransactions = entry.getValue();
            int salary = employeeSalaryMap.get(employee);

            String message = EmailBuilder.buildPayrollMessage(new BuildingEmailMessageRequest(employee, employeeTransactions, salary));
            emailSender.send(new SendingEmailRequest(message, employee.getEmail(), "Check out your salary"));
        }
    }

    private void updateTransactionsAsPaid(List<Transaction> transactions) {
        transactionRepository.saveAll(transactions);
    }


    private List<Transaction> getTransactionsByPaidIsFalse(){
        return transactionRepository.getTransactionsByPaidIsFalse();
    }
}

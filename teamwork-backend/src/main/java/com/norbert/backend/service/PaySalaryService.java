package com.norbert.backend.service;

import com.norbert.backend.dto.EmployeeDTO;
import com.norbert.backend.dto.PaySalaryDTO;
import com.norbert.backend.dto.TransactionDTO;
import com.norbert.backend.email.BuildingEmailMessageRequest;
import com.norbert.backend.email.EmailBuilder;
import com.norbert.backend.email.EmailSender;
import com.norbert.backend.email.SendingEmailRequest;
import com.norbert.backend.entity.Transaction;
import com.norbert.backend.exception.BadRequestException;
import com.norbert.backend.mapper.TransactionDTOMapper;
import com.norbert.backend.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PaySalaryService {
    private final TransactionRepository transactionRepository;
    private final EmailSender emailSender;
    private final TransactionDTOMapper transactionDTOMapper;

    public PaySalaryDTO mockPaySalary() {
        List<Transaction> transactions = getTransactionsByPaidIsFalse();
        if (transactions.isEmpty())
            throw new BadRequestException("Nothing to pay");
        List<TransactionDTO> transactionDTOS = transactions.stream().map(transactionDTOMapper).collect(Collectors.toList());
        Map<EmployeeDTO, List<TransactionDTO>> employeeTransactionsMap = createEmployeeTransactionsMap(transactionDTOS);

        Map<EmployeeDTO, Double> employeeSalaryMap = calculateEmployeeSalaries(employeeTransactionsMap);

        sendSalaryEmails(employeeTransactionsMap, employeeSalaryMap);

        transactions.forEach(transaction -> transaction.setPaid(true));
        updateTransactionsAsPaid(transactions);

        return PaySalaryDTO.builder()
                .employeeTransactionsMap(employeeTransactionsMap)
                .employeeSalaryMap(employeeSalaryMap)
                .build();
    }

    private Map<EmployeeDTO, List<TransactionDTO>> createEmployeeTransactionsMap(List<TransactionDTO> transactions) {
        Map<EmployeeDTO, List<TransactionDTO>> employeeTransactionsMap = new HashMap<>();
        for (TransactionDTO transaction : transactions) {
            List<EmployeeDTO> employees = transaction.getEmployees();
            for (EmployeeDTO employee : employees) {
                employeeTransactionsMap.computeIfAbsent(employee, k -> new ArrayList<>());
                employeeTransactionsMap.get(employee).add(transaction);
            }
        }
        return employeeTransactionsMap;
    }

    private Map<EmployeeDTO, Double> calculateEmployeeSalaries(Map<EmployeeDTO, List<TransactionDTO>> employeeTransactionsMap) {
        Map<EmployeeDTO, Double> employeeSalaryMap = new HashMap<>();
        for (Map.Entry<EmployeeDTO, List<TransactionDTO>> entry : employeeTransactionsMap.entrySet()) {
            EmployeeDTO employee = entry.getKey();
            List<TransactionDTO> employeeTransactions = entry.getValue();
            double salary = calculateSalary(employeeTransactions);
            employeeSalaryMap.put(employee, salary);
        }
        return employeeSalaryMap;
    }

    private double calculateSalary(List<TransactionDTO> employeeTransactions) {
        double salary = 0;
        for (TransactionDTO transaction : employeeTransactions) {
            salary += (double) transaction.getOrderType().getPrice() / transaction.getEmployees().size();
        }
        return salary / 2.0; // commission
    }

    private void sendSalaryEmails(Map<EmployeeDTO, List<TransactionDTO>> employeeTransactionsMap, Map<EmployeeDTO, Double> employeeSalaryMap) {
        for (Map.Entry<EmployeeDTO, List<TransactionDTO>> entry : employeeTransactionsMap.entrySet()) {
            EmployeeDTO employee = entry.getKey();
            List<TransactionDTO> employeeTransactions = entry.getValue();
            double salary = employeeSalaryMap.get(employee);

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

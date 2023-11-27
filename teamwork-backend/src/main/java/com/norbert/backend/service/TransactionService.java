package com.norbert.backend.service;

import com.norbert.backend.dao.EmployeeDAO;
import com.norbert.backend.dao.TransactionDAO;
import com.norbert.backend.dto.TransactionDTO;
import com.norbert.backend.entity.Employee;
import com.norbert.backend.entity.Transaction;
import com.norbert.backend.exception.BadRequestException;
import com.norbert.backend.mapper.TransactionDTOMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionDAO transactionDAO;
    private final TransactionDTOMapper transactionDTOMapper;
    private final EmployeeDAO employeeDAO;

    public List<TransactionDTO> getAll() {
        return transactionDAO.getAll().stream().map(transactionDTOMapper).collect(Collectors.toList());
    }

    public TransactionDTO save(Transaction transaction) {
        Set<Long> employeeIds = new HashSet<>();
        for (Employee employee : transaction.getEmployees()) {
            if (!employeeIds.add(employee.getId()))
                    throw new BadRequestException("Employees must not have duplicate");
        }
        if(employeeIds.isEmpty())
            throw new BadRequestException("Employees must be present");
        List<Employee> employees = employeeDAO.getAll();
        List<Long> existingEmployeeIds  = employees.stream().map(Employee::getId).toList();
        if(!new HashSet<>(existingEmployeeIds).containsAll(employeeIds))
            throw new BadRequestException("One or more employees do not exist");

        employees.stream()
                .filter(employee -> employeeIds.contains(employee.getId())).forEach(employee -> {
                    if (!employee.getWorking())
                        throw new BadRequestException("Employee with id " + employee.getId() + " is not working");
                });
        transaction.setPaid(false);
        Transaction savedTransaction = transactionDAO.save(transaction);
        return transactionDTOMapper.apply(savedTransaction);
    }

    public void deleteById(Long id){
        transactionDAO.deleteById(id);
    }

}

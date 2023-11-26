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

    public List<TransactionDTO> getAll() {
        return transactionDAO.getAll().stream().map(transactionDTOMapper).collect(Collectors.toList());
    }

    public TransactionDTO save(Transaction transaction) {
        Set<Long> employeeIds = new HashSet<>();

        for (Employee employee : transaction.getEmployees()) {
            if (!employeeIds.add(employee.getId())) {
                    throw new BadRequestException("Employees must not have duplicate");
            }
        }
        transaction.setPaid(false);
        Transaction savedTransaction = transactionDAO.save(transaction);
        return transactionDTOMapper.apply(savedTransaction);
    }

    public void deleteById(Long id){
        transactionDAO.deleteById(id);
    }

    public void update(Transaction transaction){
        transactionDAO.update(transaction);
    }


}

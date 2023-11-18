package com.norbert.backend.mapper;


import com.norbert.backend.dto.TransactionDTO;
import com.norbert.backend.entity.Transaction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TransactionDTOMapper implements Function<Transaction, TransactionDTO> {
    private final EmployeeDTOMapper employeeDTOMapper;
    @Override
    public TransactionDTO apply(Transaction transaction) {
        return TransactionDTO.builder()
                .id(transaction.getId())
                .employees(transaction.getEmployees().stream().map(employeeDTOMapper).collect(Collectors.toList()))
                .orderType(transaction.getOrderType())
                .date(transaction.getDate())
                .build();
    }
}


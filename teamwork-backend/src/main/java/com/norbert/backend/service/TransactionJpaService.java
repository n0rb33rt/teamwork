package com.norbert.backend.service;

import com.norbert.backend.dao.EmployeeDAO;
import com.norbert.backend.dao.TransactionDAO;
import com.norbert.backend.entity.Transaction;
import com.norbert.backend.exception.BadRequestException;
import com.norbert.backend.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class TransactionJpaService implements TransactionDAO {
    private final TransactionRepository transactionRepository;

    @Override
    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.saveAndFlush(transaction);
    }

    @Override
    public void deleteById(Long id) {
        if(!transactionRepository.existsById(id))
            throw new BadRequestException("The transaction with id " + id+ " doesn't exist");
        transactionRepository.deleteById(id);
    }

    @Override
    public void update(Transaction transaction) {
        if(!transactionRepository.existsById(transaction.getId()))
            throw new BadRequestException("The transaction with id " + transaction.getId()+ " doesn't exist");
        transactionRepository.update(transaction);
    }
}


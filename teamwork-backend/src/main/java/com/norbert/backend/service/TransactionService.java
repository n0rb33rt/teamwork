package com.norbert.backend.service;

import com.norbert.backend.dao.DAO;
import com.norbert.backend.dto.PaySalaryDTO;
import com.norbert.backend.dto.TransactionDTO;
import com.norbert.backend.entity.Transaction;
import com.norbert.backend.mapper.TransactionDTOMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionService {
    private final DAO<Transaction> transactionDAO;
    private final TransactionDTOMapper transactionDTOMapper;

    public List<TransactionDTO> getAll() {
        return transactionDAO.getAll().stream().map(transactionDTOMapper).collect(Collectors.toList());
    }

    public TransactionDTO save(Transaction transaction) {
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

package com.norbert.backend.dao;

import com.norbert.backend.entity.Transaction;

import java.util.List;

public interface TransactionDAO{
    List<Transaction> getAll();
    Transaction save(Transaction transaction);
    void deleteById(Long id);
}

package com.norbert.backend.repository;

import com.norbert.backend.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    @Query("SELECT t FROM Transactions t WHERE t.paid = false")
    List<Transaction> getTransactionsByPaidIsFalse();
}

package com.norbert.backend.repository;

import com.norbert.backend.entity.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Transactions t SET t.date = :#{#transaction.date}, t.orderType = :#{#transaction.orderType}, t.employees = :#{#transaction.employees} WHERE t.id = :#{#transaction.id}")
    void update(@Param("transaction") Transaction transaction);


    List<Transaction> getTransactionsByPaidIsFalse();
}

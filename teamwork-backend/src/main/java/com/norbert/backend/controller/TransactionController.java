package com.norbert.backend.controller;

import com.norbert.backend.dto.PaySalaryDTO;
import com.norbert.backend.dto.TransactionDTO;
import com.norbert.backend.entity.Transaction;
import com.norbert.backend.service.PaySalaryService;
import com.norbert.backend.service.TransactionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    private final TransactionService transactionService;
    private final PaySalaryService paySalary;

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getTransactions(){
        return ResponseEntity.ok(transactionService.getAll());
    }

    @PostMapping
    public ResponseEntity<TransactionDTO> saveTransaction(@RequestBody @Valid Transaction transaction){
        return ResponseEntity.ok(transactionService.save(transaction));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable("id")Long id){
        transactionService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateTransaction(@RequestBody @Valid Transaction transaction){
        transactionService.update(transaction);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/pay-salary")
    public ResponseEntity<PaySalaryDTO> paySalary(){
        return ResponseEntity.ok(paySalary.mockPaySalary());
    }
}

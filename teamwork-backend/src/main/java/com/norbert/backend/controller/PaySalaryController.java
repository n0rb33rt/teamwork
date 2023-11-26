package com.norbert.backend.controller;

import com.norbert.backend.dto.PaySalaryDTO;
import com.norbert.backend.service.PaySalaryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/salary")
public class PaySalaryController {
    private final PaySalaryService paySalaryService;
    @PostMapping()
    public ResponseEntity<PaySalaryDTO> paySalary(){
        return ResponseEntity.ok(paySalaryService.mockPaySalary());
    }
}

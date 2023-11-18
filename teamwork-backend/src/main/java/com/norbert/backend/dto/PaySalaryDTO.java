package com.norbert.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.norbert.backend.entity.Employee;
import com.norbert.backend.entity.Transaction;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaySalaryDTO {
    @JsonProperty("employee_transactions_map")
    private Map<Employee, List<Transaction>> employeeTransactionsMap;

    @JsonProperty("employee_salary_map")
    private Map<Employee,Integer> employeeSalaryMap;
}

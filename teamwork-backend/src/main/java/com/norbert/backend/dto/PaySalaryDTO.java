package com.norbert.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private Map<EmployeeDTO, List<TransactionDTO>> employeeTransactionsMap;

    @JsonProperty("employee_salary_map")
    private Map<EmployeeDTO,Double> employeeSalaryMap;
}

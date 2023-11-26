package com.norbert.backend.email;


import com.norbert.backend.dto.EmployeeDTO;
import com.norbert.backend.dto.TransactionDTO;


import java.util.List;

public record BuildingEmailMessageRequest(
        EmployeeDTO employee,
        List<TransactionDTO> transactions,
        Double salary
) {
}

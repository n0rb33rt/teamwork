package com.norbert.backend.email;


import com.norbert.backend.entity.Employee;
import com.norbert.backend.entity.Transaction;

import java.util.List;

public record BuildingEmailMessageRequest(
        Employee employee,
        List<Transaction> transactions,
        Integer salary
) {
}

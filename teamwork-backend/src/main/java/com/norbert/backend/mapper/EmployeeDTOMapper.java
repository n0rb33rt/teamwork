package com.norbert.backend.mapper;

import com.norbert.backend.dto.EmployeeDTO;
import com.norbert.backend.entity.Employee;
import org.springframework.stereotype.Component;

import java.util.function.Function;
@Component
public class EmployeeDTOMapper implements Function<Employee, EmployeeDTO> {
    @Override
    public EmployeeDTO apply(Employee employee) {
        return EmployeeDTO.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .cardNumber(employee.getCardNumber())
                .build();
    }
}

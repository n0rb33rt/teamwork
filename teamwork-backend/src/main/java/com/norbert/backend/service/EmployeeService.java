package com.norbert.backend.service;

import com.norbert.backend.dao.EmployeeDAO;
import com.norbert.backend.dto.EmployeeDTO;
import com.norbert.backend.entity.Employee;
import com.norbert.backend.mapper.EmployeeDTOMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeDAO employeeDAO;

    private final EmployeeDTOMapper employeeDTOMapper;

    public List<EmployeeDTO> getAll() {
        return employeeDAO.getAll().stream().map(employeeDTOMapper).collect(Collectors.toList());
    }

    public EmployeeDTO save(Employee employee) {
        employee.setWorking(true);
        Employee savedEmployee = employeeDAO.save(employee);
        return employeeDTOMapper.apply(savedEmployee);
    }

    public void toggleWorkingStatusById(Long id) {
        employeeDAO.toggleWorkingStatusById(id);
    }

    public void update(Employee employee) {
        employeeDAO.update(employee);
    }
}

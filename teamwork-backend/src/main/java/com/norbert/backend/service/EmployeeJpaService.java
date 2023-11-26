package com.norbert.backend.service;

import com.norbert.backend.dao.EmployeeDAO;
import com.norbert.backend.entity.Employee;
import com.norbert.backend.exception.BadRequestException;
import com.norbert.backend.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeJpaService implements EmployeeDAO{
    private final EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.saveAndFlush(employee);
    }

    @Override
    public void toggleWorkingStatusById(Long id) {
        if(!employeeRepository.existsById(id))
            throw new BadRequestException("The employee with id " + id+ " doesn't exist");
        employeeRepository.toggleWorkingStatus(id);
    }

    @Override
    public void update(Employee employee) {
        if(!employeeRepository.existsById(employee.getId()))
            throw new BadRequestException("The employee with id " + employee.getId()+ " doesn't exist");
        employeeRepository.update(employee);
    }
}

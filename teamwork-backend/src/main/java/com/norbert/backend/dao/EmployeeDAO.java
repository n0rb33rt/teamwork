package com.norbert.backend.dao;


import com.norbert.backend.entity.Employee;

import java.util.List;

public interface EmployeeDAO {
    List<Employee> getAll();
    Employee save(Employee t);
    void toggleWorkingStatusById(Long id);

    void update(Employee t);
}

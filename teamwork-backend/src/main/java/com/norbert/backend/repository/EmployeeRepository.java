package com.norbert.backend.repository;

import com.norbert.backend.entity.Employee;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Employees t SET t.email= :#{#employee.email}, t.cardNumber = :#{#employee.cardNumber}, t.firstName = :#{#employee.firstName}, t.lastName = :#{#employee.lastName} WHERE t.id = :#{#employee.id}")
    void update(@Param("employee") Employee employee);



}

package com.norbert.backend.controller;

import com.norbert.backend.dto.EmployeeDTO;
import com.norbert.backend.entity.Employee;
import com.norbert.backend.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getEmployees(){
        return ResponseEntity.ok(employeeService.getAll());
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> saveEmployee(@RequestBody @Valid Employee employee){
        return ResponseEntity.ok(employeeService.save(employee));
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<Void> toggleEmployeeWorkingStatus(@Valid @PathVariable("id")Long id){
        employeeService.toggleWorkingStatusById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateEmployee(@RequestBody @Valid Employee employee){
        employeeService.update(employee);
        return ResponseEntity.ok().build();
    }
}

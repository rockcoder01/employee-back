package com.employeeMangement.controller;

import com.employeeMangement.dto.EmployeeDTO;
import com.employeeMangement.enitities.Employee;
import com.employeeMangement.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin("*")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Get all employees
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employeeDTOs = employeeService.getAllEmployees();
        return ResponseEntity.ok(employeeDTOs);
    }

    // Get an employee by ID
    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long employeeId) {
        try {
            EmployeeDTO employeeDTO = employeeService.getEmployeeById(employeeId);
            return ResponseEntity.ok(employeeDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Add a new employee
    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee,  @RequestParam Long departmentId) {
        return employeeService.addEmployee(employee, departmentId);
    }

    // Update an employee
    @PutMapping("/{employeeId}")
    public EmployeeDTO updateEmployee(@PathVariable Long employeeId, @RequestBody Employee updatedEmployee) {
        return employeeService.updateEmployee(employeeId, updatedEmployee);
    }

    // Delete an employee
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Boolean> deleteEmployee(@PathVariable Long employeeId) {
        boolean deleted = employeeService.deleteEmployee(employeeId);

        if (deleted) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
    }
}
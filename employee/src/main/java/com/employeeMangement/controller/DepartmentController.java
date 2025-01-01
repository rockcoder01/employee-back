package com.employeeMangement.controller;

import com.employeeMangement.dto.DepartmentDTO;
import com.employeeMangement.enitities.Department;
import com.employeeMangement.enitities.Employee;
import com.employeeMangement.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/departments")
@CrossOrigin("*")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    // Get all departments
    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        List<DepartmentDTO> departments = departmentService.getAllDepartments();
        if (departments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(departments);
    }

    // Get a department by ID
    @GetMapping("/{departmentId}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long departmentId) {
        try {
            DepartmentDTO departmentDTO = departmentService.getDepartmentById(departmentId);
            return ResponseEntity.ok(departmentDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Add a new department
    @PostMapping
    public ResponseEntity<DepartmentDTO> addDepartment(@RequestBody Department department) {
        DepartmentDTO createdDepartment = departmentService.addDepartment(department);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartment);
    }

    // Update a department
    @PutMapping("/{departmentId}")
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable Long departmentId, @RequestBody Department updatedDepartment) {
        try {
            DepartmentDTO departmentDTO = departmentService.updateDepartment(departmentId, updatedDepartment);
            return ResponseEntity.ok(departmentDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Delete a department
    @DeleteMapping("/{departmentId}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long departmentId) {
        try {
            departmentService.deleteDepartment(departmentId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
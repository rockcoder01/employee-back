package com.employeeMangement.services;

import com.employeeMangement.enitities.Department;
import com.employeeMangement.enitities.Employee;
import com.employeeMangement.repository.DepartmentRepository;
import com.employeeMangement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    // Get all departments
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    // Get a department by ID
    public Department getDepartmentById(Long departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));
    }

    // Add a new department
    public Department addDepartment(Department department) {
        return departmentRepository.save(department);
    }

    // Update a department
    public Department updateDepartment(Long departmentId, Department updatedDepartment) {
        Department existingDepartment = getDepartmentById(departmentId);
        existingDepartment.setName(updatedDepartment.getName());
        existingDepartment.setLocation(updatedDepartment.getLocation());
        return departmentRepository.save(existingDepartment);
    }

    // Delete a department
    public void deleteDepartment(Long departmentId) {
        departmentRepository.deleteById(departmentId);
    }
}

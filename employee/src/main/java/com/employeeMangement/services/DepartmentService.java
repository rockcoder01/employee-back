package com.employeeMangement.services;

import com.employeeMangement.dto.DepartmentDTO;
import com.employeeMangement.enitities.Department;
import com.employeeMangement.enitities.Employee;
import com.employeeMangement.repository.DepartmentRepository;
import com.employeeMangement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;


    // Convert Department to DepartmentDTO
    private DepartmentDTO convertToDTO(Department department) {
        return new DepartmentDTO(department.getId(), department.getName(), department.getLocation());
    }


    // Convert List of Departments to List of DepartmentDTO
    private List<DepartmentDTO> convertToDTOList(List<Department> departments) {
        return departments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get all departments
    @Transactional(readOnly = true)
    public List<DepartmentDTO> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return convertToDTOList(departments);
    }

    // Get a department by ID
    @Transactional(readOnly = true)
    public DepartmentDTO getDepartmentById(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));
        return convertToDTO(department);
    }

    // Add a new department
    @Transactional
    public DepartmentDTO addDepartment(Department department) {
        Department savedDepartment = departmentRepository.save(department);
        return convertToDTO(savedDepartment);
    }

    // Update a department
    @Transactional
    public DepartmentDTO updateDepartment(Long departmentId, Department updatedDepartment) {
        Department existingDepartment = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));

        existingDepartment.setName(updatedDepartment.getName());
        existingDepartment.setLocation(updatedDepartment.getLocation());
        Department savedDepartment = departmentRepository.save(existingDepartment);
        return convertToDTO(savedDepartment);
    }

    // Delete a department
    @Transactional
    public void deleteDepartment(Long departmentId) {
        departmentRepository.deleteById(departmentId);
    }
}

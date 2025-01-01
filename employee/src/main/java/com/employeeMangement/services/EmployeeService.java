package com.employeeMangement.services;

import com.employeeMangement.dto.DepartmentDTO;
import com.employeeMangement.dto.EmployeeDTO;
import com.employeeMangement.enitities.Department;
import com.employeeMangement.enitities.Employee;
import com.employeeMangement.exception.EntityNotFoundException;
import com.employeeMangement.repository.DepartmentRepository;
import com.employeeMangement.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    // Get all employees
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get an employee by ID
    @Transactional
    public EmployeeDTO getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));
        return convertToDTO(employee);
    }

    // Add a new employee
    public Employee addEmployee(Employee employee, Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + departmentId));

        employee.setDepartment(department);

        // Save the employee with the associated department
       return employeeRepository.save(employee);
    }

    // Update an employee
    public EmployeeDTO updateEmployee(Long employeeId, Employee updatedEmployee) {

        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + employeeId));
        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setPosition(updatedEmployee.getPosition());
        existingEmployee.setSalary(updatedEmployee.getSalary());
        Employee savedEmployee = employeeRepository.save(existingEmployee);
        return  convertToDTO(savedEmployee);
    }

    // Delete an employee
    @Transactional
    public boolean deleteEmployee(Long employeeId) {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);

        if (!employeeOpt.isPresent()) {
            throw new EntityNotFoundException("Employee with ID " + employeeId + " not found");
        }

        Employee employee = employeeOpt.get();

        Department department = employee.getDepartment();
        if (department != null) {
            department.getEmployees().remove(employee);
            departmentRepository.save(department);
        }

        employeeRepository.deleteById(employeeId);

        return true;
    }

    // Convert Employee entity to EmployeeDTO
    private EmployeeDTO convertToDTO(Employee employee) {
        Department department = employee.getDepartment();
        DepartmentDTO departmentDTO = null;

        if (department != null) {
            departmentDTO = new DepartmentDTO(department.getId(), department.getName(), department.getLocation());
        }

        return new EmployeeDTO(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getPosition(),
                employee.getSalary(),
                departmentDTO
        );
    }
}


package com.employeeMangement.services;

import com.employeeMangement.enitities.Department;
import com.employeeMangement.enitities.Employee;
import com.employeeMangement.repository.DepartmentRepository;
import com.employeeMangement.repository.EmployeeRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class ReportGenerateService {
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentRepository departmentRepository;

    public void generateEmployeeReport() throws JRException, IOException {
        // Step 1: Fetch employees from the database
        Optional<Department> departmentOptional = departmentRepository.findById(1l);
        if (!departmentOptional.isPresent()) {
            throw new RuntimeException("Department not found with ID: " + 1l);
        }

        Department department = departmentOptional.get();

        // Prepare data for the report
        List<Department> departmentList = Collections.singletonList(department);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(departmentList);

        // Load the JRXML file
        ClassPathResource reportResource = new ClassPathResource("reports/employee_report.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(reportResource.getInputStream());

        // Set parameters
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ReportTitle", "department Report");

        // Fill the report with data
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Export the report to a byte array
         JasperExportManager.exportReportToPdfFile(jasperPrint, "employee_report.pdf");
        System.out.println("Employee Report generated successfully!");
    }
}

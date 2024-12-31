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
        List<Department> departmentList  = departmentRepository.findAll();
//        if (!departmentOptional.isPresent()) {
//            throw new RuntimeException("Department not found with ID: " + 1l);
//        }
//
//        Department department = departmentOptional.get();
//
//        // Prepare data for the report
//        List<Department> departmentList = Collections.singletonList(department);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(departmentList.get(0).getEmployees());

        // Load the JRXML file
        ClassPathResource reportResource = new ClassPathResource("reports/department_master_report.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(reportResource.getInputStream());

//        ClassPathResource subReportResource = new ClassPathResource("reports/employee_subreport.jrxml");
//        JasperReport subReport = JasperCompileManager.compileReport(subReportResource.getInputStream());


        // Set parameters
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ReportTitle", "department Report");
        // Prepare parameters
//        parameters.put("departmentName", department.getName());
//        parameters.put("departmentLocation", department.getLocation());
        parameters.put("departmentList", departmentList);

//        parameters.put("SubreportPath", subReport); // Pass the compiled subreport
        // Fill the report with data
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Export the report to a byte array
         JasperExportManager.exportReportToPdfFile(jasperPrint, "employee_report.pdf");
        System.out.println("Employee Report generated successfully!");
    }
}

package com.employeeMangement.services;

import com.employeeMangement.enitities.Department;
import com.employeeMangement.enitities.Employee;
import com.employeeMangement.repository.DepartmentRepository;
import com.employeeMangement.repository.EmployeeRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class ReportGenerateService {
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentRepository departmentRepository;

    public void generateEmployeeReport() throws JRException {
        // Step 1: Fetch employees from the database
        Long id = 1L;
        Optional<Department> departmentOptional = departmentRepository.findById(id);

        if (departmentOptional.isPresent()) {
            Department department = departmentOptional.get();

            // Step 2: Prepare data for the report (using JRBeanCollectionDataSource)
            List<Department> departmentList = new ArrayList<>();
            departmentList.add(department);  // Adding the department to a list

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(departmentList);

            File file = new File("src/main/resources/reports/employee_report.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

            // Step 4: Set parameters for the report (if any)
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("ReportTitle", "Employees in Each Department");

            // Step 5: Fill the report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Step 6: Export to PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, "employee_report.pdf");
        }

        // Step 3: Load the JRXML file (template)


        System.out.println("Employee Report generated successfully!");
    }
}

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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class ReportGenerateService {
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentRepository departmentRepository;

    public byte[] generateEmployeeReport(Long id) throws JRException, IOException {
        Optional<Department> departmentOptional  = departmentRepository.findById(id);
        if (!departmentOptional.isPresent()) {
            throw new RuntimeException("Department not found with ID: " + id);
        }
        Department department = departmentOptional.get();

        // Prepare data for the report
        List<Department> departmentList = Collections.singletonList(department);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(departmentList.get(0).getEmployees());

        // Load the JRXML file
        ClassPathResource reportResource = new ClassPathResource("reports/employee_report.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(reportResource.getInputStream());

        // Set parameters
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("departmentName", department.getName());
        parameters.put("departmentLocation", department.getLocation());
        parameters.put("departmentList", departmentList);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

//         JasperExportManager.exportReportToPdfFile(jasperPrint, "employee_report.pdf");
        // Export the report to a byte array (PDF format in this case)
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
    }
}

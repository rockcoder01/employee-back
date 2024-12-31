package com.employeeMangement.controller;

import com.employeeMangement.services.ReportGenerateService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/reports")
public class ReportGenerateController {

    @Autowired
    private ReportGenerateService employeeReportService;

    @GetMapping("/generate")
    public ResponseEntity<String> generateEmployeeReport() {
        try {
            employeeReportService.generateEmployeeReport();
            return ResponseEntity.ok("Employee report generated successfully.");
        } catch (JRException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating report." + e.getMessage());
        }
    }
}


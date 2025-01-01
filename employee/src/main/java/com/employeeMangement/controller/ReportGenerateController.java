package com.employeeMangement.controller;

import com.employeeMangement.services.ReportGenerateService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/reports")
@CrossOrigin("*")
public class ReportGenerateController {

    @Autowired
    private ReportGenerateService employeeReportService;

    @GetMapping("/generatebydepartment/{id}")
    public ResponseEntity<byte[]> generateEmployeeReport(@PathVariable Long id) {
        try {
            byte[] reportBytes = employeeReportService.generateEmployeeReport(id);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF) // Specify content type if it's a PDF
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employee_report_department_" + id + ".pdf")
                    .body(reportBytes);
        } catch (JRException | IOException e) {
            // Handle error and return an appropriate error message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error generating report: " + e.getMessage()).getBytes());
        }
    }
}


package fpt.qn.mock_day_1.controller;

import fpt.qn.mock_day_1.dto.EmployeeUtilizationReportDTO;
import fpt.qn.mock_day_1.dto.EmployeeWorkloadResponseDTO;
import fpt.qn.mock_day_1.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    // API 6.3: GET /employees/{id}/workload
    @GetMapping("/employees/{code}/workload")
    public ResponseEntity<EmployeeWorkloadResponseDTO> getEmployeeWorkload(@PathVariable("code") String code) {
        EmployeeWorkloadResponseDTO response = reportService.getEmployeeWorkload(code);
        return ResponseEntity.ok(response);
    }

    // Report 4.1: GET /reports/utilization
    @GetMapping("/reports/utilization")
    public ResponseEntity<List<EmployeeUtilizationReportDTO>> getUtilizationReport() {
        return ResponseEntity.ok(reportService.getUtilizationReport());
    }

    // Report 4.2: GET /reports/available-resources
    @GetMapping("/reports/available")
    public ResponseEntity<List<EmployeeUtilizationReportDTO>> getAvailableResources() {
        return ResponseEntity.ok(reportService.getAvailableResources());
    }

    // Report 4.3: GET /reports/overloaded
    @GetMapping("/reports/overloaded")
    public ResponseEntity<List<EmployeeUtilizationReportDTO>> getOverloadedEmployees() {
        return ResponseEntity.ok(reportService.getOverloadedEmployees());
    }
}

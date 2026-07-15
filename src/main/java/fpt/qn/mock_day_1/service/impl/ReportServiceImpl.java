package fpt.qn.mock_day_1.service.impl;

import fpt.qn.mock_day_1.dto.EmployeeUtilizationReportDTO;
import fpt.qn.mock_day_1.dto.EmployeeWorkloadResponseDTO;
import fpt.qn.mock_day_1.entity.Employee;
import fpt.qn.mock_day_1.repository.AllocationRepository;
import fpt.qn.mock_day_1.repository.EmployeeRepository;
import fpt.qn.mock_day_1.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final EmployeeRepository employeeRepository;
    private final AllocationRepository allocationRepository;

    @Override
    @Transactional(readOnly = true)
    public EmployeeWorkloadResponseDTO getEmployeeWorkload(String id) {
        Employee employee = employeeRepository.findByEmployeeCode(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy nhân viên với ID: " + id));

        int totalAllocation = allocationRepository.getTotalAllocationPercentByEmployeeCode(id);
        int available = 100 - totalAllocation;

        return EmployeeWorkloadResponseDTO.builder()
                .employeeId(employee.getEmployeeId())
                .employeeName(employee.getFullName())
                .totalAllocation(totalAllocation)
                .available(Math.max(0, available)) // Đảm bảo không bị âm
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeUtilizationReportDTO> getUtilizationReport() {
        // Report 4.1: Tổng allocation của toàn bộ nhân viên
        return employeeRepository.getEmployeeUtilizationReport();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeUtilizationReportDTO> getAvailableResources() {
        // Report 4.2: Nhân viên có thời gian khả dụng (Allocation < 100%)
        return employeeRepository.getEmployeeUtilizationReport().stream()
                .filter(report -> report.getTotalAllocation() < 100)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeUtilizationReportDTO> getOverloadedEmployees() {
        // Report 4.3: Workload cao quá tải (Allocation > 90%)
        return employeeRepository.getEmployeeUtilizationReport().stream()
                .filter(report -> report.getTotalAllocation() > 90)
                .collect(Collectors.toList());
    }
}

package fpt.qn.mock_day_1.service;

import fpt.qn.mock_day_1.dto.EmployeeUtilizationReportDTO;
import fpt.qn.mock_day_1.dto.EmployeeWorkloadResponseDTO;
import java.util.List;

public interface ReportService {
    EmployeeWorkloadResponseDTO getEmployeeWorkload(String code);

    List<EmployeeUtilizationReportDTO> getUtilizationReport();

    List<EmployeeUtilizationReportDTO> getAvailableResources();

    List<EmployeeUtilizationReportDTO> getOverloadedEmployees();
}

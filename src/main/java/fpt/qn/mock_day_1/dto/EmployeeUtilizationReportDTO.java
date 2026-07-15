package fpt.qn.mock_day_1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeUtilizationReportDTO {
    private Long employeeId;
    private String employeeCode;
    private String fullName;
    private Long totalAllocation;
    private Long available;
}

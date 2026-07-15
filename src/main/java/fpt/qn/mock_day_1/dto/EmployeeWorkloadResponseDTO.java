package fpt.qn.mock_day_1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeWorkloadResponseDTO {
    private Long employeeId;
    private String employeeName;
    private Integer totalAllocation;
    private Integer available;
}

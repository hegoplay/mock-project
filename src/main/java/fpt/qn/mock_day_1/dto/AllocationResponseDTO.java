package fpt.qn.mock_day_1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllocationResponseDTO {
    private Long allocationId;
    private String employeeCode;
    private String fullName;
    private String projectCode;
    private String projectName;
    private Integer allocationPercent;
    private String roleInProject;
    private LocalDate startDate;
    private LocalDate endDate;
}

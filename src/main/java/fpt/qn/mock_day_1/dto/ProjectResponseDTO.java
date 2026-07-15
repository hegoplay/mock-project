package fpt.qn.mock_day_1.dto;

import fpt.qn.mock_day_1.entity.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponseDTO {
    private Long projectId;
    private String projectCode;
    private String projectName;
    private String customer;
    private ProjectStatus status;
}

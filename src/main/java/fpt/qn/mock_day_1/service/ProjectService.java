package fpt.qn.mock_day_1.service;

import fpt.qn.mock_day_1.dto.ProjectRequestDTO;
import fpt.qn.mock_day_1.dto.ProjectResponseDTO;
import java.util.List;

public interface ProjectService {
    ProjectResponseDTO createProject(ProjectRequestDTO requestDTO);

    List<ProjectResponseDTO> getAllProjects();

    ProjectResponseDTO getProjectByCode(String code); // Thay đổi từ ID sang Code

    ProjectResponseDTO updateProject(String code, ProjectRequestDTO requestDTO);
}

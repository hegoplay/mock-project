package fpt.qn.mock_day_1.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fpt.qn.mock_day_1.dto.ProjectRequestDTO;
import fpt.qn.mock_day_1.dto.ProjectResponseDTO;
import fpt.qn.mock_day_1.entity.Project;
import fpt.qn.mock_day_1.repository.ProjectRepository;
import fpt.qn.mock_day_1.service.ProjectService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public ProjectResponseDTO createProject(ProjectRequestDTO requestDTO) {
        // Kiểm tra trùng mã dự án
        if (projectRepository.findByProjectCode(requestDTO.getProjectCode()).isPresent()) {
            throw new IllegalArgumentException("Mã dự án '" + requestDTO.getProjectCode() + "' đã tồn tại!");
        }

        Project project = new Project();
        project.setProjectCode(requestDTO.getProjectCode());
        project.setProjectName(requestDTO.getProjectName());
        project.setCustomer(requestDTO.getCustomer());
        project.setStatus(requestDTO.getStatus()); // Lưu tên enum dưới dạng String vào DB

        Project savedProject = projectRepository.save(project);
        return mapToResponseDTO(savedProject);
    }

    @Override
    public List<ProjectResponseDTO> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectResponseDTO getProjectByCode(String code) {
        // Tìm kiếm trực tiếp bằng Code
        Project project = projectRepository.findByProjectCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy dự án với mã: " + code));
        return mapToResponseDTO(project);
    }

    @Override
    @Transactional
    public ProjectResponseDTO updateProject(String code, ProjectRequestDTO requestDTO) {
        // 1. Tìm dự án theo code cũ
        Project project = projectRepository.findByProjectCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy dự án với mã: " + code));

        // 2. Kiểm tra nếu muốn đổi code khác và code đó đã tồn tại ở dự án khác
        if (!project.getProjectCode().equals(requestDTO.getProjectCode())) {
            if (projectRepository.findByProjectCode(requestDTO.getProjectCode()).isPresent()) {
                throw new IllegalArgumentException("Mã dự án '" + requestDTO.getProjectCode() + "' đã tồn tại!");
            }
            project.setProjectCode(requestDTO.getProjectCode());
        }

        // 3. Cập nhật các thông tin còn lại
        project.setProjectName(requestDTO.getProjectName());
        project.setCustomer(requestDTO.getCustomer());
        project.setStatus(requestDTO.getStatus());

        Project savedProject = projectRepository.save(project);
        return mapToResponseDTO(savedProject);
    }

    private ProjectResponseDTO mapToResponseDTO(Project project) {
        return ProjectResponseDTO.builder()
                .projectId(project.getProjectId())
                .projectCode(project.getProjectCode())
                .projectName(project.getProjectName())
                .customer(project.getCustomer())
                .status(project.getStatus()) // Convert String từ DB sang Enum
                .build();
    }
}

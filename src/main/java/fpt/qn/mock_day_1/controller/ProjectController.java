package fpt.qn.mock_day_1.controller;

import fpt.qn.mock_day_1.dto.ProjectRequestDTO;
import fpt.qn.mock_day_1.dto.ProjectResponseDTO;
import fpt.qn.mock_day_1.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    // 1. POST /projects - Tạo mới dự án
    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(@Valid @RequestBody ProjectRequestDTO requestDTO) {
        ProjectResponseDTO createdProject = projectService.createProject(requestDTO);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    // 2. GET /projects - Lấy toàn bộ danh sách
    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> getAllProjects() {
        List<ProjectResponseDTO> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    // 3. GET /projects/{code} - Lấy chi tiết theo Code thay vì ID số
    @GetMapping("/{code}")
    public ResponseEntity<ProjectResponseDTO> getProjectByCode(@PathVariable("code") String code) {
        ProjectResponseDTO project = projectService.getProjectByCode(code);
        return ResponseEntity.ok(project);
    }

    // 4. PUT /projects/{code} - Cập nhật thông tin dự án
    @PutMapping("/{code}")
    public ResponseEntity<ProjectResponseDTO> updateProject(
            @PathVariable("code") String code,
            @Valid @RequestBody ProjectRequestDTO requestDTO) {
        ProjectResponseDTO updatedProject = projectService.updateProject(code, requestDTO);
        return ResponseEntity.ok(updatedProject);
    }
}

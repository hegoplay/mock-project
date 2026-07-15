package fpt.qn.mock_day_1.service.impl;

import fpt.qn.mock_day_1.dto.AllocationRequestDTO;
import fpt.qn.mock_day_1.dto.AllocationResponseDTO;
import fpt.qn.mock_day_1.entity.Allocation;
import fpt.qn.mock_day_1.entity.Employee;
import fpt.qn.mock_day_1.entity.Project;
import fpt.qn.mock_day_1.entity.ProjectStatus;
import fpt.qn.mock_day_1.mapper.AllocationMapper;
import fpt.qn.mock_day_1.repository.AllocationRepository;
import fpt.qn.mock_day_1.repository.EmployeeRepository;
import fpt.qn.mock_day_1.repository.ProjectRepository;
import fpt.qn.mock_day_1.service.AllocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AllocationServiceImpl implements AllocationService {

    private final AllocationRepository allocationRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final AllocationMapper allocationMapper;

    @Override
    @Transactional
    public AllocationResponseDTO createAllocation(AllocationRequestDTO requestDTO) {
        Employee employee = employeeRepository.findByEmployeeCode(requestDTO.getEmployeeCode())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Không tìm thấy nhân viên: " + requestDTO.getEmployeeCode()));

        Project project = projectRepository.findByProjectCode(requestDTO.getProjectCode())
                .orElseThrow(
                        () -> new IllegalArgumentException("Không tìm thấy dự án: " + requestDTO.getProjectCode()));

        if (project.getStatus() == ProjectStatus.COMPLETED) {
            throw new IllegalArgumentException("Không thể allocate nhân viên vào dự án đã HOÀN THÀNH (COMPLETED)");
        }

        int currentTotal = allocationRepository.getTotalAllocationPercentByEmployeeCode(employee.getEmployeeCode());
        if (currentTotal + requestDTO.getAllocationPercent() > 100) {
            throw new IllegalArgumentException("Employee allocation exceeds 100%");
        }

        Allocation allocation = allocationMapper.toEntity(requestDTO);
        allocation.setEmployee(employee);
        allocation.setProject(project);

        Allocation savedAllocation = allocationRepository.save(allocation);
        return allocationMapper.toResponseDTO(savedAllocation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AllocationResponseDTO> getAllAllocations() {
        return allocationMapper.toResponseList(allocationRepository.findAll());
    }

    @Override
    @Transactional
    public AllocationResponseDTO updateAllocation(Long id, AllocationRequestDTO requestDTO) {
        // 1. Tìm bản ghi Allocation cũ
        Allocation existingAllocation = allocationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy bản ghi Allocation với ID: " + id));

        // 2. Tìm Employee và Project mới (nếu có đổi)
        Employee employee = employeeRepository.findByEmployeeCode(requestDTO.getEmployeeCode())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Không tìm thấy nhân viên: " + requestDTO.getEmployeeCode()));

        Project project = projectRepository.findByProjectCode(requestDTO.getProjectCode())
                .orElseThrow(
                        () -> new IllegalArgumentException("Không tìm thấy dự án: " + requestDTO.getProjectCode()));

        // [Business Rule 3]: Không allocate vào dự án COMPLETED
        if (project.getStatus() == ProjectStatus.COMPLETED) {
            throw new IllegalArgumentException("Không thể allocate nhân viên vào dự án đã HOÀN THÀNH (COMPLETED)");
        }

        // [Business Rule 2]: Tổng allocation không được vượt quá 100% (Loại bỏ chính
        // bản ghi đang update)
        int currentTotalExcludeSelf = allocationRepository.getTotalAllocationExcludeCurrent(employee.getEmployeeCode(),
                id);
        if (currentTotalExcludeSelf + requestDTO.getAllocationPercent() > 100) {
            throw new IllegalArgumentException("Employee allocation exceeds 100%");
        }

        // 3. Cập nhật dữ liệu mới
        existingAllocation.setEmployee(employee);
        existingAllocation.setProject(project);
        existingAllocation.setAllocationPercent(requestDTO.getAllocationPercent());
        existingAllocation.setRoleInProject(requestDTO.getRoleInProject());
        existingAllocation.setStartDate(requestDTO.getStartDate());
        existingAllocation.setEndDate(requestDTO.getEndDate());

        Allocation updatedAllocation = allocationRepository.save(existingAllocation);
        return allocationMapper.toResponseDTO(updatedAllocation);
    }
}

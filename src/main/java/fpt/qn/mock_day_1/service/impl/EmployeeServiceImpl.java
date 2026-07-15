package fpt.qn.mock_day_1.service.impl;

import fpt.qn.mock_day_1.dto.EmployeeRequestDTO;
import fpt.qn.mock_day_1.dto.EmployeeResponseDTO;
import fpt.qn.mock_day_1.entity.Employee;
import fpt.qn.mock_day_1.repository.EmployeeRepository;
import fpt.qn.mock_day_1.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO requestDTO) {
        // 1. Kiểm tra trùng mã nhân viên
        if (employeeRepository.findByEmployeeCode(requestDTO.getEmployeeCode()).isPresent()) {
            throw new IllegalArgumentException("Mã nhân viên '" + requestDTO.getEmployeeCode() + "' đã tồn tại!");
        }

        // 2. Map từ DTO sang Entity để lưu xuống DB
        Employee employee = new Employee();
        employee.setEmployeeCode(requestDTO.getEmployeeCode());
        employee.setFullName(requestDTO.getFullName());
        employee.setEmail(requestDTO.getEmail());
        employee.setRole(requestDTO.getRole());
        employee.setDepartment(requestDTO.getDepartment());

        Employee savedEmployee = employeeRepository.save(employee);

        // 3. Trả về Response DTO
        return mapToResponseDTO(savedEmployee);
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeResponseDTO getEmployeeByCode(String code) {
        Employee employee = employeeRepository.findByEmployeeCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy nhân viên với Code: " + code));
        return mapToResponseDTO(employee);
    }

    @Override
    @Transactional
    public EmployeeResponseDTO updateEmployee(String code, EmployeeRequestDTO requestDTO) {
        // 1. Tìm nhân viên theo code cũ
        Employee employee = employeeRepository.findByEmployeeCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy nhân viên với Code: " + code));

        // 2. Kiểm tra nếu muốn đổi code khác và code đó đã tồn tại ở nhân viên khác
        if (!employee.getEmployeeCode().equals(requestDTO.getEmployeeCode())) {
            if (employeeRepository.findByEmployeeCode(requestDTO.getEmployeeCode()).isPresent()) {
                throw new IllegalArgumentException("Mã nhân viên '" + requestDTO.getEmployeeCode() + "' đã tồn tại!");
            }
            employee.setEmployeeCode(requestDTO.getEmployeeCode());
        }

        // 3. Cập nhật các thông tin còn lại
        employee.setFullName(requestDTO.getFullName());
        employee.setEmail(requestDTO.getEmail());
        employee.setRole(requestDTO.getRole());
        employee.setDepartment(requestDTO.getDepartment());

        Employee savedEmployee = employeeRepository.save(employee);
        return mapToResponseDTO(savedEmployee);
    }

    // Hàm helper hỗ trợ map nhanh từ Entity sang Response DTO
    private EmployeeResponseDTO mapToResponseDTO(Employee employee) {
        return EmployeeResponseDTO.builder()
                .employeeId(employee.getEmployeeId())
                .employeeCode(employee.getEmployeeCode())
                .fullName(employee.getFullName())
                .email(employee.getEmail())
                .role(employee.getRole())
                .department(employee.getDepartment())
                .build();
    }
}

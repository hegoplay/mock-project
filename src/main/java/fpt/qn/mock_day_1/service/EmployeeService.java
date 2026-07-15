package fpt.qn.mock_day_1.service;

import fpt.qn.mock_day_1.dto.EmployeeRequestDTO;
import fpt.qn.mock_day_1.dto.EmployeeResponseDTO;
import java.util.List;

public interface EmployeeService {
    EmployeeResponseDTO createEmployee(EmployeeRequestDTO requestDTO);

    List<EmployeeResponseDTO> getAllEmployees();

    EmployeeResponseDTO getEmployeeByCode(String code);

    EmployeeResponseDTO updateEmployee(String code, EmployeeRequestDTO requestDTO);
}

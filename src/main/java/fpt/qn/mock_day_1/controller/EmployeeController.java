package fpt.qn.mock_day_1.controller;

import fpt.qn.mock_day_1.dto.EmployeeRequestDTO;
import fpt.qn.mock_day_1.dto.EmployeeResponseDTO;
import fpt.qn.mock_day_1.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    // 1. POST /employees
    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@Valid @RequestBody EmployeeRequestDTO requestDTO) {
        EmployeeResponseDTO createdEmployee = employeeService.createEmployee(requestDTO);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    // 2. GET /employees
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        List<EmployeeResponseDTO> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    // 3. GET /employees/{id}
    @GetMapping("/{code}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable("code") String code) {
        EmployeeResponseDTO employee = employeeService.getEmployeeByCode(code);
        return ResponseEntity.ok(employee);
    }

    // 4. PUT /employees/{code}
    @PutMapping("/{code}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(
            @PathVariable("code") String code,
            @Valid @RequestBody EmployeeRequestDTO requestDTO) {
        EmployeeResponseDTO updatedEmployee = employeeService.updateEmployee(code, requestDTO);
        return ResponseEntity.ok(updatedEmployee);
    }
}

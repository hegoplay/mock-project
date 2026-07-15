package fpt.qn.mock_day_1.controller;

import fpt.qn.mock_day_1.dto.AllocationRequestDTO;
import fpt.qn.mock_day_1.dto.AllocationResponseDTO;
import fpt.qn.mock_day_1.service.AllocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/allocations")
@RequiredArgsConstructor
public class AllocationController {

    private final AllocationService allocationService;

    // 1. POST /allocations - Tạo mới allocation
    @PostMapping
    public ResponseEntity<AllocationResponseDTO> createAllocation(@Valid @RequestBody AllocationRequestDTO requestDTO) {
        AllocationResponseDTO response = allocationService.createAllocation(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 2. GET /allocations - Lấy danh sách toàn bộ allocation (Mới bổ sung)
    @GetMapping
    public ResponseEntity<List<AllocationResponseDTO>> getAllAllocations() {
        List<AllocationResponseDTO> responseList = allocationService.getAllAllocations();
        return ResponseEntity.ok(responseList);
    }

    // 3. PUT /allocations/{id} - Cập nhật thông tin Allocation
    @PutMapping("/{id}")
    public ResponseEntity<AllocationResponseDTO> updateAllocation(
            @PathVariable("id") Long id,
            @Valid @RequestBody AllocationRequestDTO requestDTO) {
        AllocationResponseDTO response = allocationService.updateAllocation(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    // @ExceptionHandler(IllegalArgumentException.class)
    // public ResponseEntity<Map<String, String>>
    // handleBusinessErrors(IllegalArgumentException ex) {
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message",
    // ex.getMessage()));
    // }
}

package fpt.qn.mock_day_1.service;

import fpt.qn.mock_day_1.dto.AllocationRequestDTO;
import fpt.qn.mock_day_1.dto.AllocationResponseDTO;
import java.util.List;

public interface AllocationService {
    AllocationResponseDTO createAllocation(AllocationRequestDTO requestDTO);

    List<AllocationResponseDTO> getAllAllocations(); // Thêm API GET List

    AllocationResponseDTO updateAllocation(Long id, AllocationRequestDTO requestDTO);
}

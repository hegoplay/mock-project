package fpt.qn.mock_day_1.mapper;

import fpt.qn.mock_day_1.dto.AllocationRequestDTO;
import fpt.qn.mock_day_1.dto.AllocationResponseDTO;
import fpt.qn.mock_day_1.entity.Allocation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring") // Đăng ký Mapper này như một Spring Bean
public interface AllocationMapper {

    // Map từ RequestDTO sang Entity (Bỏ qua các trường liên kết Employee và Project
    // để set thủ công sau)
    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "allocationId", ignore = true)
    Allocation toEntity(AllocationRequestDTO requestDTO);

    // Map từ Entity sang ResponseDTO (MapStruct tự động trích xuất thuộc tính từ
    // object con lồng nhau)
    @Mapping(source = "employee.employeeCode", target = "employeeCode")
    @Mapping(source = "employee.fullName", target = "fullName")
    @Mapping(source = "project.projectCode", target = "projectCode")
    @Mapping(source = "project.projectName", target = "projectName")
    AllocationResponseDTO toResponseDTO(Allocation allocation);

    // Map danh sách Entity sang danh sách ResponseDTO cho API GET List
    List<AllocationResponseDTO> toResponseList(List<Allocation> allocations);
}

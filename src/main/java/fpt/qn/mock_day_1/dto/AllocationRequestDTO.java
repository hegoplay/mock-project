package fpt.qn.mock_day_1.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllocationRequestDTO {

    @NotBlank(message = "Mã nhân viên không được để trống")
    private String employeeCode;

    @NotBlank(message = "Mã dự án không được để trống")
    private String projectCode;

    @NotNull(message = "Phần trăm allocation không được để trống")
    @Min(value = 1, message = "Allocation phải lớn hơn 0%")
    @Max(value = 100, message = "Allocation không được vượt quá 100%")
    private Integer allocationPercent;

    @NotBlank(message = "Vai trò trong dự án không được để trống")
    private String roleInProject;

    @NotNull(message = "Ngày bắt đầu không được để trống")
    private LocalDate startDate;

    private LocalDate endDate;
}

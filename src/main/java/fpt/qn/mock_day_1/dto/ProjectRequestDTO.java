package fpt.qn.mock_day_1.dto;

import fpt.qn.mock_day_1.entity.ProjectStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequestDTO {

    @NotBlank(message = "Mã dự án không được để trống")
    @Size(max = 20, message = "Mã dự án không được vượt quá 20 ký tự")
    private String projectCode;

    @NotBlank(message = "Tên dự án không được để trống")
    @Size(max = 200, message = "Tên dự án không được vượt quá 200 ký tự")
    private String projectName;

    @NotBlank(message = "Tên khách hàng không được để trống")
    @Size(max = 100, message = "Tên khách hàng không được vượt quá 100 ký tự")
    private String customer;

    @NotNull(message = "Trạng thái không được để trống")
    private ProjectStatus status; // Sử dụng Enum để validate trực tiếp đầu vào
}

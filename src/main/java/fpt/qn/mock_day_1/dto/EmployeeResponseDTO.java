package fpt.qn.mock_day_1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // Dùng Builder pattern để map dữ liệu cho tiện
public class EmployeeResponseDTO {
    private Long employeeId;
    private String employeeCode;
    private String fullName;
    private String email;
    private String role;
    private String department;
}

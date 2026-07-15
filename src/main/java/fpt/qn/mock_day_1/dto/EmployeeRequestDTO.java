package fpt.qn.mock_day_1.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestDTO {

    @NotBlank(message = "Mã nhân viên không được để trống")
    @Size(max = 20, message = "Mã nhân viên không vượt quá 20 ký tự")
    private String employeeCode;

    @NotBlank(message = "Họ và tên không được để trống")
    @Size(max = 100, message = "Họ và tên không vượt quá 100 ký tự")
    private String fullName;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    @Size(max = 100, message = "Email không vượt quá 100 ký tự")
    private String email;

    @NotBlank(message = "Chức vụ không được để trống")
    @Size(max = 50, message = "Chức vụ không vượt quá 50 ký tự")
    private String role;

    @NotBlank(message = "Phòng ban không được để trống")
    @Size(max = 50, message = "Phòng ban không vượt quá 50 ký tự")
    private String department;
}

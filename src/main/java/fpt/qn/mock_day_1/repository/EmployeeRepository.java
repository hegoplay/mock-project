package fpt.qn.mock_day_1.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fpt.qn.mock_day_1.dto.EmployeeUtilizationReportDTO;
import fpt.qn.mock_day_1.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Tìm kiếm nhanh theo mã nhân viên (vì có ràng buộc UNIQUE)
    Optional<Employee> findByEmployeeCode(String employeeCode);

    // JPQL truy vấn báo cáo sử dụng tài nguyên (Utilization Report)
    @Query("SELECT new fpt.qn.mock_day_1.dto.EmployeeUtilizationReportDTO(" +
            "e.employeeId, e.employeeCode, e.fullName, " +
            "COALESCE(SUM(a.allocationPercent), 0), " +
            "(100 - COALESCE(SUM(a.allocationPercent), 0))) " +
            "FROM Employee e " +
            "LEFT JOIN Allocation a ON e.employeeId = a.employee.employeeId " +
            "GROUP BY e.employeeId, e.employeeCode, e.fullName")
    List<EmployeeUtilizationReportDTO> getEmployeeUtilizationReport();
}

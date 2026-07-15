package fpt.qn.mock_day_1.repository;

import fpt.qn.mock_day_1.entity.Allocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AllocationRepository extends JpaRepository<Allocation, Long> {

    // Tính tổng phần trăm allocation hiện tại của nhân viên
    @Query("SELECT COALESCE(SUM(a.allocationPercent), 0) FROM Allocation a WHERE a.employee.employeeCode = :employeeCode")
    Integer getTotalAllocationPercentByEmployeeCode(@Param("employeeCode") String employeeCode);

    @Query("SELECT COALESCE(SUM(a.allocationPercent), 0) FROM Allocation a " +
            "WHERE a.employee.employeeCode = :employeeCode AND a.allocationId != :excludeAllocationId")
    int getTotalAllocationExcludeCurrent(@Param("employeeCode") String employeeCode,
            @Param("excludeAllocationId") Long excludeAllocationId);
}

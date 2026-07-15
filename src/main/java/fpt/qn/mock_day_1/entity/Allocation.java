package fpt.qn.mock_day_1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "allocation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Allocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "allocation_id")
    private Long allocationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    private Project project;

    @Column(name = "allocation_percent")
    private Integer allocationPercent;

    @Column(name = "role_in_project", length = 100)
    private String roleInProject;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @PrePersist
    @PreUpdate
    private void validateConstraints() {
        // 1. Check phần trăm allocation
        if (allocationPercent != null && (allocationPercent < 0 || allocationPercent > 100)) {
            throw new IllegalStateException("Allocation percent phải nằm trong khoảng 0 - 100!");
        }

        // 2. Check ngày bắt đầu và kết thúc
        if (startDate != null && endDate != null && endDate.isBefore(startDate)) {
            throw new IllegalStateException("Ngày kết thúc phải sau ngày bắt đầu!");
        }
    }
}

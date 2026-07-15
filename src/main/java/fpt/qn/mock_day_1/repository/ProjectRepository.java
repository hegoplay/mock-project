package fpt.qn.mock_day_1.repository;

import fpt.qn.mock_day_1.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    // Tìm kiếm theo code để phục vụ yêu cầu "ID convert sang Code"
    Optional<Project> findByProjectCode(String projectCode);
}

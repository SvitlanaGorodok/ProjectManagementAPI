package api.projectmanagement.repository;

import api.projectmanagement.model.dao.EmployeeLevelDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeeLevelRepository extends JpaRepository<EmployeeLevelDao, UUID> {
}

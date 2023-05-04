package api.projectmanagement.repository;

import api.projectmanagement.model.dao.EmployeeLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeLevelRepository extends JpaRepository<EmployeeLevel, Integer> {
}

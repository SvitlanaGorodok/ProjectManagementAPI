package api.projectmanagement.repository;

import api.projectmanagement.model.dao.EmployeeDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeDao, Integer> {
}

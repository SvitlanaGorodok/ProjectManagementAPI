package api.projectmanagement.repository;

import api.projectmanagement.model.dao.EmployeeDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeDao, UUID> {
    @Query(value = "SELECT e.email FROM employees e", nativeQuery  = true)
    List<String> findAllEmails();
}

package api.projectmanagement.repository;

import api.projectmanagement.model.dao.EmployeeDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeDao, UUID> {
    Optional<EmployeeDao> findByEmail(String email);
}

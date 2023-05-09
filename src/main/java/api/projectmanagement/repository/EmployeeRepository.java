package api.projectmanagement.repository;

import api.projectmanagement.model.dao.EmployeeDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeDao, UUID> {
    @Query(value = "SELECT e.email FROM employees e", nativeQuery  = true)
    List<String> findAllEmails();

    @Query(value = "SELECT e.* FROM employees e " +
            "WHERE LOWER(e.first_name) LIKE :firstName OR LOWER(e.last_name) LIKE :lastName " +
            "OR LOWER(e.email) LIKE :email OR e.position_id = :positionId OR e.level_id = :levelId",
            nativeQuery  = true)
    List<EmployeeDao> findByParameters(@Param("firstName") String firstName, @Param("lastName") String lastName,
                                       @Param("email") String email, @Param("positionId") UUID positionId,
                                       @Param("levelId") UUID levelId);

}

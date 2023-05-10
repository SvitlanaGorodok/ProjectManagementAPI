package api.projectmanagement.repository;

import api.projectmanagement.model.dao.ProjectDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectDao, UUID> {
    @Query(value = "SELECT p.name FROM projects p", nativeQuery = true)
    List<String> findAllNames();

    @Query(value = "SELECT p.* FROM projects p " +
            "WHERE LOWER(p.name) LIKE :name " +
            "AND p.start_date >= COALESCE( :startDateFrom , p.start_date) " +
            "AND p.start_date <= COALESCE( :startDateTo , p.start_date)" +
            "AND p.end_date >= COALESCE( :endDateFrom , p.end_date) " +
            "AND p.end_date <= COALESCE( :endDateTo , p.end_date)",
            nativeQuery = true)
    List<ProjectDao> findByParameters(@Param("name") String name, @Param("startDateFrom") Date startDateFrom,
                                       @Param("startDateTo") Date startDateTo,
                                       @Param("endDateFrom") Date endDateFrom, @Param("endDateTo") Date endDateTo);
}

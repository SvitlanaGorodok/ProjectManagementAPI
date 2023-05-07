package api.projectmanagement.repository;

import api.projectmanagement.model.dao.ProjectDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectDao, UUID> {
    @Query(value = "SELECT p.name FROM projects p", nativeQuery  = true)
    List<String> findAllNames();
}

package api.projectmanagement.repository;

import api.projectmanagement.model.dao.ProjectDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectDao, UUID> {
}

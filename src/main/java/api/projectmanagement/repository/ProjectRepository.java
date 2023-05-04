package api.projectmanagement.repository;

import api.projectmanagement.model.dao.ProjectDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectDao, Integer> {
}

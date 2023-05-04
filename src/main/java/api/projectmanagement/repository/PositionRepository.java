package api.projectmanagement.repository;

import api.projectmanagement.model.dao.PositionDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<PositionDao, Integer> {
}

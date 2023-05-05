package api.projectmanagement.repository;

import api.projectmanagement.model.dao.PositionDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PositionRepository extends JpaRepository<PositionDao, UUID> {
}

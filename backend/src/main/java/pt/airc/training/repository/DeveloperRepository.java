package pt.airc.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.airc.training.models.Developer;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {
}

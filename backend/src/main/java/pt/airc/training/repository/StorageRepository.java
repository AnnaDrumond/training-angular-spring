package pt.airc.training.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import pt.airc.training.models.Storage;

import java.util.List;

public interface StorageRepository extends JpaRepository<Storage, Long> {

    List<Storage> findAll(Sort sort);

    @Query(value ="SELECT q.capacity FROM storage q WHERE q.id =:id", nativeQuery = true)
    Integer findCapacityById(Long id);
}

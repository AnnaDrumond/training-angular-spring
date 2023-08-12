package pt.airc.training.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import pt.airc.training.models.StorageDrink;
import pt.airc.training.specifications.StorageDrinkSpec;

import java.util.Optional;


public interface StorageDrinkRepository extends JpaRepository<StorageDrink, Long> {

    //storage_drink é o nome da tabela da db
    @Query(value = "SELECT SUM(q.quantity) FROM storage_drink q WHERE q.storage_id =:id", nativeQuery = true)
    Long getTotalDrinksByStorageId(Long id);
    Page<StorageDrink> findAll(Specification<StorageDrinkSpec> spec, Pageable pageable);

    Boolean existsByDrinkId(Long id);

    Optional<StorageDrink> findByDrinkIdAndStorageId(Long idDrink , Long idStorage);

    //Page<StorageDrink> findAllByStorageId(Pageable pageable, Long id);

}

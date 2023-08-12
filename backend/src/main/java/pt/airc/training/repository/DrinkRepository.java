package pt.airc.training.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import pt.airc.training.models.Drink;

import java.util.List;
import java.util.Optional;

public interface DrinkRepository extends JpaRepository<Drink, Long> {
    List<Drink> findAllByDeletedAtNull();

    Page<Drink> findAll(Specification<Drink> spec,Pageable pageable);

    Optional <Drink> findById (Long id);

    Long countByBrandIdAndDeletedAtIsNull(Long id);

    List<Drink> findAllByBrandIdAndDeletedAtIsNull(Long id);
}

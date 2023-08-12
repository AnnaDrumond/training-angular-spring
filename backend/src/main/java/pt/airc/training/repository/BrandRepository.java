package pt.airc.training.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import pt.airc.training.models.Brand;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Page<Brand> findAllByDeletedAtNull(Specification<Brand> spec, Pageable pageable);

    Page<Brand> findAll(Specification<Brand> spec, Pageable pageable);

    List<Brand> findAllByDeletedAtNull();

}

package pt.airc.training.services;

import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pt.airc.training.dtos.BrandDTO;
import pt.airc.training.exceptions.AircTrainingException;
import pt.airc.training.mappers.BrandMapper;

import pt.airc.training.models.Brand;
import pt.airc.training.repository.BrandRepository;
import pt.airc.training.repository.DrinkRepository;
import pt.airc.training.specifications.BrandSpecs;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;
    private final DrinkRepository drinkRepository;
    private final BrandMapper brandMapper;

    public Page<BrandDTO> listAllIsDeletedAtNull(Pageable pageable, String filter) {
        Specification<Brand> filtersAux = BrandSpecs.filters(filter);
        return this.brandRepository.findAll(filtersAux, pageable).map(this.brandMapper::toDTO);
    }

    public List<BrandDTO> listAllIsDeletedAtNull() {
        return this.brandRepository.findAllByDeletedAtNull().stream()
               .map(this.brandMapper::toDTO).collect(Collectors.toList());
    }

    public void doSoftDelete(Long id) {
        Brand brand = this.brandRepository.findById(id).orElseThrow(() -> {
            throw new AircTrainingException(HttpStatus.NOT_FOUND,
                    "Não encontrada entidada para chave primária " + id);
        });

        if (this.drinkRepository.countByBrandIdAndDeletedAtIsNull(id) > 0) {
            throw new AircTrainingException(HttpStatus.CONFLICT, "Operação não permitida. Esta marca possui bebidas associadas.");
        }
        brand.setDeletedAt(Timestamp.from(ZonedDateTime.now().toInstant()));
        this.brandRepository.save(brand);
    }

    public BrandDTO create(BrandDTO brandDTO) {
        Brand brand = this.brandMapper.toModel(brandDTO);
        this.brandRepository.save(brand);
        return this.brandMapper.toDTO(brand);
    }

}

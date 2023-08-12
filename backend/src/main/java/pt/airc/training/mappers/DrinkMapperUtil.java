package pt.airc.training.mappers;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import pt.airc.training.models.Brand;
import pt.airc.training.repository.BrandRepository;


import javax.persistence.EntityNotFoundException;

@Component
@RequiredArgsConstructor
public class DrinkMapperUtil {

    private final BrandRepository brandRepository;

    @Named("getBrandById")
    public Brand getBrandById(Long id) {
        return this.brandRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}

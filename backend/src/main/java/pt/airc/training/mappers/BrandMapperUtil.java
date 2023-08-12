package pt.airc.training.mappers;


import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import pt.airc.training.repository.DrinkRepository;

@Component
@RequiredArgsConstructor
public class BrandMapperUtil {

    private final DrinkRepository drinkRepository;

    @Named("countDrinksByBrandId")
    public Long countDrinksByBrandId(Long id) {
        return this.drinkRepository.countByBrandIdAndDeletedAtIsNull(id);
    }


}

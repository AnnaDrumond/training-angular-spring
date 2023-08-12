package pt.airc.training.mappers;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import pt.airc.training.exceptions.AircTrainingException;
import pt.airc.training.models.Drink;
import pt.airc.training.models.Storage;
import pt.airc.training.models.StorageDrink;
import pt.airc.training.repository.DrinkRepository;
import pt.airc.training.repository.StorageRepository;

@Component
@RequiredArgsConstructor
public class StorageDrinkMapperUtil {

    private final StorageRepository storageRepository;
    private final DrinkRepository drinkRepository;

    @Named("getStorageById")
    public Storage getStorageById(Long id) {
        return this.storageRepository.findById(id).orElseThrow(() -> {
            throw new AircTrainingException(HttpStatus.NOT_FOUND,
                    "Não encontrada entidada para chave primária " + id);
        });
    }

    @Named("getDrinkById")
    public Drink getDrinkById(Long id) {
        return this.drinkRepository.findById(id).orElseThrow(() -> {
            throw new AircTrainingException(HttpStatus.NOT_FOUND,
                    "Não encontrada entidada para chave primária " + id);
        });
    }

}

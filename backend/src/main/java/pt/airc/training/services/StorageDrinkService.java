package pt.airc.training.services;

import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pt.airc.training.dtos.StorageDrinkDTO;

import pt.airc.training.exceptions.AircTrainingException;
import pt.airc.training.mappers.StorageDrinkMapper;

import pt.airc.training.models.Storage;
import pt.airc.training.models.StorageDrink;
import pt.airc.training.repository.DrinkRepository;
import pt.airc.training.repository.StorageDrinkRepository;
import pt.airc.training.repository.StorageRepository;
import pt.airc.training.specifications.StorageDrinkSpec;

@Slf4j
@Service
@AllArgsConstructor
public class StorageDrinkService {

    private final StorageDrinkMapper storageDrinkMapper;
    private final StorageDrinkRepository storageDrinkRepository;
    private final DrinkRepository drinkRepository;

    private final StorageRepository storageRepository;


    public Page<StorageDrinkDTO> getDataByStorageId(Pageable pageable, Long id, String filter) {
        Specification<StorageDrinkSpec> filtersAux = StorageDrinkSpec.filters(filter, id);
        log.info("voltei do spec e vou ao repository com filtersAux " + filtersAux);
        return this.storageDrinkRepository.findAll(filtersAux, pageable)
                .map(this.storageDrinkMapper::toDTO);
    }

    //A lógica para buscar a drink e a storage estarão no respetivo mapper/mapperUtil
    public void addDrinkInStorage(StorageDrinkDTO storageDrinkDTO) {
        log.info("addDrinkInStorage");
        StorageDrink storageDrink = this.storageDrinkRepository.findByDrinkIdAndStorageId(storageDrinkDTO.getIdDrink(), storageDrinkDTO.getIdStorage())
                .orElseGet(() -> this.storageDrinkMapper.ToModel(storageDrinkDTO)); //Verificar se já existe esta bebida neste armazem
        verifyStorageCapacity(storageDrinkDTO.getQuantity(), storageDrink.getStorage());
        System.out.println("vou levar a base de dados " + (storageDrink.getQuantity() + storageDrinkDTO.getQuantity()));
        storageDrink.setQuantity(storageDrink.getQuantity() + storageDrinkDTO.getQuantity());//Se quantity fosse um BigDecimal daria para fazer .add()
        this.storageDrinkRepository.save(storageDrink);
    }
    private void verifyStorageCapacity(Integer quantity, Storage storage) {
        if (quantity > storage.getCapacity() - this.storageDrinkRepository.getTotalDrinksByStorageId(storage.getId())) {
            throw new AircTrainingException(HttpStatus.CONFLICT, "Não existe espaço suficiente neste armazem");
        }
    }
}

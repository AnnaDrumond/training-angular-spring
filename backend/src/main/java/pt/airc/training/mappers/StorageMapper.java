package pt.airc.training.mappers;

import org.mapstruct.*;
import pt.airc.training.dtos.StorageDTO;
import pt.airc.training.models.Storage;

@Mapper(componentModel = "spring", uses = {StorageMapperUtil.class})
public interface StorageMapper {

    @Mapping(target = "id", ignore = true)
    Storage ToModel(StorageDTO storageDTO);

    @Mapping(target = "totalDrinksStored", source = "id", qualifiedByName = "countDrinksByStorageId")
    @Mapping(target = "remainingCapacity", source = "id", qualifiedByName = "calculateRemainingCapacity")
    StorageDTO toDTO(Storage storage);
}

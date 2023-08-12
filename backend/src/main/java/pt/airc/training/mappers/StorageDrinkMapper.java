package pt.airc.training.mappers;

import org.mapstruct.*;
import pt.airc.training.dtos.DrinkDTO;
import pt.airc.training.dtos.StorageDrinkDTO;
import pt.airc.training.models.Drink;
import pt.airc.training.models.StorageDrink;

@Mapper(componentModel = "spring", uses = {StorageDrinkMapperUtil.class})
public interface StorageDrinkMapper {

    @Mapping(target = "drink", source = "idDrink", qualifiedByName = "getDrinkById")
    @Mapping(target = "storage", source = "idStorage", qualifiedByName = "getStorageById")
    StorageDrink ToModel(StorageDrinkDTO storageDrinkDto);

    @Mapping(target = "brandName", source = "drink.brand.name")
    @Mapping(target = "drinkVolume", source = "drink.volumeMl")
    @Mapping(target = "drinkName", source = "drink.name")
    @Mapping(target = "idDrink", source = "drink.id")
    @Mapping(target = "idStorage", source = "storage.id")
    StorageDrinkDTO toDTO(StorageDrink storageDrink);




}

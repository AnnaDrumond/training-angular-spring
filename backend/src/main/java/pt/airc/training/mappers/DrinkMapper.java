package pt.airc.training.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import org.mapstruct.MappingTarget;
import pt.airc.training.dtos.DrinkDTO;
import pt.airc.training.models.Drink;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DrinkMapperUtil.class})
public interface DrinkMapper {

    @Mapping(target = "nameBrand", expression = "java((drink.getBrand().getName()))")
    @Mapping(target = "idBrand", expression = "java((drink.getBrand().getId()))")
    DrinkDTO toDTO(Drink drink);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "brand", source = "idBrand", qualifiedByName = "getBrandById")
    Drink ToModel(DrinkDTO drinkDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "brand", source = "idBrand", qualifiedByName = "getBrandById")
    Drink updateToEntityFromDto(DrinkDTO drinkDTO , @MappingTarget Drink drink);

    List<DrinkDTO> listEntitiesToListDto(List<Drink> drinks);
}

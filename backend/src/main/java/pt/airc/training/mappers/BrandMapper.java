package pt.airc.training.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import pt.airc.training.dtos.BrandDTO;
import pt.airc.training.models.Brand;


@Mapper(componentModel = "spring", uses = {BrandMapperUtil.class})
public interface BrandMapper {

    @Mapping(target = "numberOfDrinks", source = "id", qualifiedByName = "countDrinksByBrandId")
    BrandDTO toDTO(Brand brand);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "drinks", ignore = true)
    Brand toModel(BrandDTO brandDTO);



}

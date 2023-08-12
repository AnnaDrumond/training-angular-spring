package pt.airc.training.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TypeDrinkConverter implements AttributeConverter<TypeDrink, Short> {

    @Override
    public Short convertToDatabaseColumn(TypeDrink typeDrink) {
        return TypeDrink.convertToDatabaseColumn(typeDrink);
    }

    @Override
    public TypeDrink convertToEntityAttribute(Short drinkCode) {
        return TypeDrink.convertToEntityAttribute(drinkCode);
    }
}

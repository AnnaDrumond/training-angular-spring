package pt.airc.training.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TypeDrink {

    AGUA((short)1,"Agua"),
    SUMOS((short)2,"Sumos"),
    NECTARES((short)3,"Nectares"),
    ALCOOOLICAS((short) 4,"Alcoolicas"),
    OUTRAS((short)5 ,"Outras");

    private final Short drinkCode;
    private final String typeValue;
    private static Map<Short, TypeDrink> drinkCodeToEnumValuesMapping = new LinkedHashMap<>();

    static {
        for (TypeDrink typeDrink : TypeDrink.values()) {
            drinkCodeToEnumValuesMapping.put(typeDrink.getDrinkCode(), typeDrink);
        }
    }
    public static Short convertToDatabaseColumn(TypeDrink typeDrink) {
        return typeDrink.drinkCode;
    }
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static TypeDrink convertToEntityAttribute(Short drinkCode) {
        return drinkCodeToEnumValuesMapping.get(drinkCode);
    }
}

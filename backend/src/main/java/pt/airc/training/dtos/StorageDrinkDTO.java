package pt.airc.training.dtos;

import lombok.*;
import pt.airc.training.models.Drink;
import pt.airc.training.models.Storage;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link pt.airc.training.models.StorageDrink} entity
 */
@Builder
@ToString @Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class StorageDrinkDTO {

    @NotNull(message = "quantidade é obrigatório")
    private  Integer quantity;

    private String brandName;
    private Integer drinkVolume;
    private String drinkName;

    @NotNull(message = "idDrink é obrigatório")
    private Long idDrink;

    @NotNull(message = "idStorage é obrigatório")
    private Long idStorage;

}
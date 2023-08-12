package pt.airc.training.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@ToString @Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class StorageDTO {

    private  Long id;
    @NotBlank(message = "Código é obrigatório")
    private  String code;
    @NotBlank(message = "Localização é obrigatório")
    private  String location;
    @NotNull(message = "Capacidade é obrigatório")
    private  Integer capacity;
    private Integer totalDrinksStored;
    private Integer remainingCapacity;
}
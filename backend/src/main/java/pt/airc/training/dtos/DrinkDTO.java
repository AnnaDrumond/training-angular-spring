package pt.airc.training.dtos;

import lombok.*;
import pt.airc.training.enums.TypeDrink;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Builder
@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DrinkDTO {


    private Long id;

    @Size(max = 30)
    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @NotNull(message = "Preço é obrigatório")
    private BigDecimal unitPrice;

    @NotNull(message = "Volume é obrigatório")
    private Integer volumeMl;

    @NotNull(message = "Tipo é obrigatório")
    private TypeDrink type;

    private boolean discontinued;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;
    private String nameBrand;

    @NotNull(message = "IdBrand é obrigatório")
    private Long idBrand;
}

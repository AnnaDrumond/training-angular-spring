package pt.airc.training.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
public class BrandDTO {

    private Long id;

    @Size(max = 50)
    @NotBlank(message = "Volume é obrigatório")
    private String source;

    @Size(max = 30)
    @NotBlank(message = "Nome é obrigatório")
    private String name;

    private Long numberOfDrinks;
}

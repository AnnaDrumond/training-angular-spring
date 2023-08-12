package pt.airc.training.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StorageDrink_PK implements Serializable {

    private Long storage;
    private Long drink;
}

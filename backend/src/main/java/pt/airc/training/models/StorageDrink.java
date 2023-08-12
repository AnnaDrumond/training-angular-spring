package pt.airc.training.models;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "storage_drink")
@IdClass(StorageDrink_PK.class)
public class StorageDrink {

    @Column(nullable = false)
    private Integer quantity;
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "storage_id", nullable = false)
    private Storage storage;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "drink_id", nullable = false)
    private Drink drink;
}

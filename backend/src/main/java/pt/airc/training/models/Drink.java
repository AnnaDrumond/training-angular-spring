package pt.airc.training.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import pt.airc.training.enums.TypeDrink;
import pt.airc.training.enums.TypeDrinkConverter;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Drink {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "increment")
    private Long id;
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @NotNull
    @Column(name="name", nullable = false)
    private String name;

    @NotNull
    @Column(name="unit_price", nullable = false)
    private BigDecimal unitPrice;

    @NotNull
    @Column(name="volume_ml", nullable = false)
    private Integer volumeMl;

    @NotNull
    @Column(name="type", nullable = false)
    @Convert(converter = TypeDrinkConverter.class)
    private TypeDrink type;

    @CreationTimestamp
    @Column(name="created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name="updated_at")
    private Timestamp updatedAt;

    @Column(name="deleted_at")
    private Timestamp deletedAt;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "drink")
    private Set<StorageDrink> storageDrinksList = new HashSet<>();


}

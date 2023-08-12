package pt.airc.training.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Brand {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "increment")
    private Long id;

    @NotNull
    @Column(name="source", nullable = false)
    private String source;

    @NotNull
    @Column(name="name", nullable = false)
    private String name;

    @Column(name="deleted_at")
    private Timestamp deletedAt;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "brand")
    private Set<Drink> drinks = new HashSet<>();
}

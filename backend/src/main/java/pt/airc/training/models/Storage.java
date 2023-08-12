package pt.airc.training.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
//https://www.baeldung.com/jpa-unique-constraints
//https://www.w3schools.com/sql/sql_unique.asp
@Table(uniqueConstraints = {@UniqueConstraint(
        name = "unique_code_location", columnNames = {"code", "location"})})
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "increment")
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String code;

    @NotNull
    @Column(nullable = false)
    private String location;

    @NotNull
    @Column(nullable = false)
    private Integer capacity;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "storage")
    private Set<StorageDrink> storageDrinks = new HashSet<>();

}
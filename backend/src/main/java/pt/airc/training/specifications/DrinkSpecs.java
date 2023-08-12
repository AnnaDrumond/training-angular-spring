package pt.airc.training.specifications;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import pt.airc.training.enums.TypeDrink;
import pt.airc.training.models.Brand;
import pt.airc.training.models.Brand_;
import pt.airc.training.models.Drink;
import pt.airc.training.models.Drink_;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static pt.airc.training.enums.TypeDrink.convertToEntityAttribute;

@Slf4j
public class DrinkSpecs {

    public static Specification<Drink> filters(String filter) {
        log.info("SPECIFICATION");
        return (root, query, criteriaBuilder) -> {
            //Poderia ser (para esta consulta simples) apenas Join<Drink, Brand> joinBrand = root.join(Drink_.brand)
            //https://www.geeksforgeeks.org/sql-join-set-1-inner-left-right-and-full-joins/
            Join<Drink, Brand> joinBrand = root.join(Drink_.brand, JoinType.LEFT);
            if (StringUtils.isNotBlank(filter)) {
                return criteriaBuilder.and(getSearchPredicates(filter, root, criteriaBuilder, joinBrand),
                        criteriaBuilder.isNull(root.get(Drink_.deletedAt)));
            }
            return criteriaBuilder.conjunction();
        };
    }

    private static Predicate getSearchPredicates(String filter, Root<Drink> root,
                                                 CriteriaBuilder criteriaBuilder, Join<Drink, Brand> joinBrand) {
        List<Predicate> predicates = new ArrayList<>();
        String filterPrepared = prepareFilter(filter);
        for (TypeDrink typeDrink : EnumSet.allOf(TypeDrink.class)) {
            //Se meu Enum typeValue tivesse acentos teria de tratar os acentos
            //String normalizedFilter = Normalizer.normalize(filter, Normalizer.Form.NFD);
            //E pesquisar por algo tipo normalizedFilter.toLowerCase().replaceAll("\\p{M}", "")
            if (typeDrink.getTypeValue().toLowerCase().contains(filter.toLowerCase())) {
                System.out.println("IF type drink " + filter);
                filter = String.valueOf(convertToEntityAttribute(typeDrink.getDrinkCode()));
                predicates.add(criteriaBuilder.equal(root.get(Drink_.type), TypeDrink.valueOf(filter)));
            }
        }
        System.out.println("depois do if");
        predicates.add(criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.lower(root.get(Drink_.name)), filterPrepared),

                criteriaBuilder.like(criteriaBuilder.lower(joinBrand.get(Brand_.name)), filterPrepared)));
        return criteriaBuilder.or(predicates.toArray(new Predicate[]{}));
    }

    private static String prepareFilter(String filter) {
        filter = "%" + filter.trim() + "%";
        return filter.toLowerCase();
    }
}

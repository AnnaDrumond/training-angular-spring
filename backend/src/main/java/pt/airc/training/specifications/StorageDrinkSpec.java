package pt.airc.training.specifications;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import pt.airc.training.models.*;
import pt.airc.training.models.Drink_;

import javax.persistence.criteria.*;


@Slf4j
@ToString
public class StorageDrinkSpec {

    //nome bebida e nome brand
    public static Specification<StorageDrinkSpec> filters(String filter, Long id) {
        return (root, query, criteriaBuilder) -> {

            Join<StorageDrink, Drink> joinDrink = root.join(StorageDrink_.DRINK, JoinType.LEFT);
            Join<Drink, Brand> joinBrand = joinDrink.join(Drink_.brand);
            Join<StorageDrink, Storage> joinStorage = root.join(StorageDrink_.STORAGE, JoinType.LEFT);

            if (StringUtils.isNotBlank(filter)) {
                return criteriaBuilder.and(getSearchPredicates(filter, root, criteriaBuilder, joinDrink, joinBrand),
                        criteriaBuilder.equal(joinStorage.get(Storage_.id), id));
            }
            return criteriaBuilder.conjunction();
        };
    }

    private static Predicate getSearchPredicates(String filter, Root<StorageDrinkSpec> root,
                                                 CriteriaBuilder criteriaBuilder, Join<StorageDrink, Drink> joinDrink,
                                                 Join<Drink, Brand> joinBrand) {
        String filterPrepared = prepareFilter(filter);
        return criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.lower(
                        joinDrink.get(Drink_.name)), filterPrepared),
        criteriaBuilder.like(criteriaBuilder.lower(
                joinBrand.get(Brand_.name)), filterPrepared));
    }

    private static String prepareFilter(String filter) {
        filter = "%" + filter.trim() + "%";
        return filter.toLowerCase();
    }
}

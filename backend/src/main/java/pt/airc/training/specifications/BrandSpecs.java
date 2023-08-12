package pt.airc.training.specifications;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import pt.airc.training.models.Brand;
import pt.airc.training.models.Brand_;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@ToString
public class BrandSpecs {
    public static Specification<Brand> filters(String filter) {
        return (root, query, criteriaBuilder) -> {

            //https://www.baeldung.com/jpa-criteria-api-in-expressions
            //Subquery<Classe> subquery = query.subquery(Classe.class);

            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(filter)) {
                getSearchPredicates(filter, root, criteriaBuilder, predicates);
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }


            return criteriaBuilder.conjunction();
        };
    }

    private static void getSearchPredicates(String filter, Root<Brand> root,
                                            CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        predicates.add(criteriaBuilder.or(

                criteriaBuilder.and(
                    criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(Brand_.name)), "%" + filter.toLowerCase() + "%"),
                        criteriaBuilder.isNull(root.get(Brand_.deletedAt))),

                criteriaBuilder.and(
                    criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(Brand_.source)), "%" + filter.toLowerCase() + "%"),
                        criteriaBuilder.isNull(root.get(Brand_.deletedAt)))));
    }
}

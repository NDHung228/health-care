package likelion.edu.vn.health_care.util;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;

public class SpecificationUtil {
    public static <T> Specification<T> likeIgnoreCase(String attribute, String value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(attribute)), "%" + value.toLowerCase() + "%");
    }

    public static <T> Specification<T> equal(String attribute, Object value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(attribute), value);
    }

    public static <T> Specification<T> betweenDates(String attribute, LocalDate startDate, LocalDate endDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get(attribute), startDate, endDate);
    }

    public static <T> Specification<T> in(String attribute, List<?> values) {
        return (root, query, criteriaBuilder) ->
                root.get(attribute).in(values);
    }
}

package likelion.edu.vn.health_care.util;

import org.springframework.data.jpa.domain.Specification;

public class SpecificationUtil {
    public static <T> Specification<T> likeIgnoreCase(String attribute, String value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(attribute)), "%" + value.toLowerCase() + "%");
    }
}

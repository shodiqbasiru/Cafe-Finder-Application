package com.msfb.cafe_finder_application.specification;

import com.msfb.cafe_finder_application.dto.request.PageCafeRequest;
import com.msfb.cafe_finder_application.entity.Cafe;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CafeSpecification {
    public static Specification<Cafe> getSpecification(PageCafeRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.getCafeName() != null) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("cafeName")), "%" + request.getCafeName() .toLowerCase()+ "%")
                );
            }
            if (request.getLocation() != null) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("location")),
                                "%" + request.getLocation().toLowerCase() + "%"
                        )
                );
            }
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
    }
}

package com.example.querydinamic.specifications;

import com.example.querydinamic.criteries.SearchCriteria;
import com.example.querydinamic.entities.BirthChild;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Collection;

public class BirthChildSpecification implements Specification<BirthChild> {
    private final SearchCriteria criteria;

    public BirthChildSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<BirthChild> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Class<?> type = root.get(criteria.getKey()).getJavaType();
        switch (criteria.getOperation().toLowerCase()) {
            case "eq" -> {
                return builder.equal(
                        builder.upper(root.get(criteria.getKey())), criteria.getValue().toString().toUpperCase());
            }
            case "lt" -> {
                if (type == LocalDate.class) {
                    return builder.lessThan(root.get(criteria.getKey()), (LocalDate) criteria.getValue());
                } else {
                    return builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString());
                }
            }
            case "gt" -> {
                if (type == LocalDate.class) {
                    return builder.greaterThan(root.get(criteria.getKey()), (LocalDate) criteria.getValue());
                } else {
                    return builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());
                }
            }
            case "le" -> {
                if (type == LocalDate.class) {
                    return builder.lessThanOrEqualTo(root.get(criteria.getKey()), (LocalDate) criteria.getValue());
                } else {
                    return builder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString());
                }
            }
            case "ge" -> {
                if (type == LocalDate.class) {
                    return builder.greaterThanOrEqualTo(root.get(criteria.getKey()), (LocalDate) criteria.getValue());
                } else {
                    return builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString());
                }
            }
            case "like" -> {
                return builder.like(
                        builder.upper(root.get(criteria.getKey())), criteria.getValue().toString().toUpperCase());
            }
            case "in" -> {
                if (criteria.getValue() instanceof Collection) {
                    return builder.upper(root.get(criteria.getKey())).in((Collection) criteria.getValue());
                }
                return null;
            }
            case "not" -> {
                if (criteria.getValue() instanceof Collection) {
                    return builder.not(builder.upper(root.get(criteria.getKey())).in((Collection) criteria.getValue()));
                }
                return null;
            }
            default -> {
                return null;
            }
        }
    }
}

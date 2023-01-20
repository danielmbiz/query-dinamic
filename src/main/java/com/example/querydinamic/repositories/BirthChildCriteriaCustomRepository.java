package com.example.querydinamic.repositories;

import com.example.querydinamic.criteries.BirthChildFilterParam;
import com.example.querydinamic.entities.BirthChild;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BirthChildCriteriaCustomRepository {
    private final EntityManager entityManager;

    public BirthChildCriteriaCustomRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<BirthChild> findCriteria(BirthChildFilterParam params) {

        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<BirthChild> query = criteriaBuilder.createQuery(BirthChild.class);
        Root<BirthChild> birthChild = query.from(BirthChild.class);
        List<Predicate> predicates = new ArrayList<>();

        if (params.getName() != null) {
            predicates.add(
                    criteriaBuilder.like(
                            criteriaBuilder.upper(
                                    birthChild.get("name")),
                            "%" + params.getName().toUpperCase() + "%"));
        }
        if (params.getBirth() != null) {
            predicates.add(
                    criteriaBuilder.equal(
                            birthChild.get("birth"),
                            params.getBirth()));
        }
        if (params.getFather() != null) {
            predicates.add(
                    criteriaBuilder.like(
                            criteriaBuilder.upper(
                                    birthChild.get("father")),
                            "%" + params.getFather().toUpperCase() + "%"));
        }
        if (params.getMon() != null) {
            predicates.add(
                    criteriaBuilder.equal(
                            criteriaBuilder.upper(
                                    birthChild.get("mon")),
                            "%" + params.getMon().toUpperCase() + "%"));
        }
        if (params.getCity() != null) {
            predicates.add(
                    criteriaBuilder.like(
                            criteriaBuilder.upper(
                                    birthChild.get("city")),
                            "%" + params.getCity().toUpperCase() + "%"));
        }

        if (!predicates.isEmpty()) {
            query.where(predicates.toArray(Predicate[]::new));
        }

        TypedQuery<BirthChild> queryResult = this.entityManager.createQuery(query);

        return queryResult.getResultList();
    }
}

package com.example.querydinamic.repositories;

import com.example.querydinamic.entities.BirthChild;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BirthChildCustomRepository {

    private EntityManager entityManager;
    public BirthChildCustomRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<BirthChild> findCustom(Long id, String name, String city) {
        String query = "SELECT c FROM BirthChild AS c";
        String condition = " where ";
        if (id != null) {
            query += condition + " c.id = :id";
            condition = " and ";
        }
        if (name != null) {
            query += condition + " c.name = :name";
            condition = " and ";
        }
        if (city != null) {
            query += condition + " c.city = :city";
            condition = " and ";
        }

        var createQuery = entityManager.createQuery(query, BirthChild.class);
        if (id != null) {
            createQuery.setParameter("id", id);
        }
        if (name != null) {
            createQuery.setParameter("name", name);
        }
        if (city != null) {
            createQuery.setParameter("city", city);
        }

        return createQuery.getResultList();
    }
}

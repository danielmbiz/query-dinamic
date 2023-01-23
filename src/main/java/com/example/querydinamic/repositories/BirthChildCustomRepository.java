package com.example.querydinamic.repositories;

import com.example.querydinamic.entities.BirthChild;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class BirthChildCustomRepository {

    private final EntityManager entityManager;

    public BirthChildCustomRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<BirthChild> findCustom(String name, LocalDate birth, String father, String mon, String city) {
        String query = "SELECT c FROM BirthChild AS c";
        String condition = " where ";
        if (name != null) {
            query += condition + " UPPER(c.name) like CONCAT('%', :name, '%') ";
            condition = " and ";
        }
        if (birth != null) {
            query += condition + " c.birth = :birth";
            condition = " and ";
        }
        if (father != null) {
            query += condition + " UPPER(c.father) like CONCAT('%', :father, '%') ";
            condition = " and ";
        }
        if (mon != null) {
            query += condition + " UPPER(c.mon) like CONCAT('%', :mon, '%') ";
            condition = " and ";
        }
        if (city != null) {
            query += condition + " UPPER(c.city) like CONCAT('%', :city, '%') ";
        }

        var createQuery = entityManager.createQuery(query, BirthChild.class);
        if (name != null) {
            createQuery.setParameter("name", name.toUpperCase());
        }
        if (birth != null) {
            createQuery.setParameter("birth", birth);
        }
        if (father != null) {
            createQuery.setParameter("father", father.toUpperCase());
        }
        if (mon != null) {
            createQuery.setParameter("mon !=", mon.toUpperCase());
        }
        if (city != null) {
            createQuery.setParameter("city", city.toUpperCase());
        }

        return createQuery.getResultList();
    }

    public List<BirthChild> findCustomFilter(String filter) {
        String where = "";
        if (filter != null) {
            String[] commands = filter.split(";");
            where = whereCondition(commands);
        }

        String query = "SELECT c FROM BirthChild AS c " + where;
        TypedQuery<BirthChild> createQuery = entityManager.createQuery(query, BirthChild.class);
        return createQuery.getResultList();
    }

    private String whereCondition(String[] commands) {
        String condition = " WHERE ";
        StringBuilder where = new StringBuilder();

        for (int i = 0; i <= commands.length - 1; i++) {
            if (commands[i].contains(" eq ") || commands[i].contains(" == ")) {
                where.append(condition);
                where.append(commands[i].replaceAll(" eq ", " = '").replaceAll(" == ", " = '"));
                where.append("'");
                condition = " AND ";
            }
            if (commands[i].contains(" lt ") || commands[i].contains(" <: ")) {
                where.append(condition);
                where.append(commands[i].replaceAll(" lt ", " < ").replaceAll(" <: ", " < "));
                condition = " AND ";
            }
            if (commands[i].contains(" gt ") || commands[i].contains(" >: ")) {
                where.append(condition);
                where.append(commands[i].replaceAll(" gt ", " > ").replaceAll(" >: ", " > "));
                condition = " AND ";
            }
            if (commands[i].contains(" le ") || commands[i].contains(" <= ")) {
                where.append(condition);
                where.append(commands[i].replaceAll(" le ", " <= "));
                condition = " AND ";
            }
            if (commands[i].contains(" ge ") || commands[i].contains(" >= ")) {
                where.append(condition);
                where.append(commands[i].replaceAll(" ge ", " >= "));
                condition = " AND ";
            }
            if (commands[i].contains(" in ")) {
                where.append(condition);
                where.append(commands[i].replaceAll(" in ", " IN( "));
                where.append(")");
                condition = " AND ";
            }
            if (commands[i].contains(" not ")) {
                where.append(condition);
                where.append(commands[i].replaceAll(" not ", " NOT IN( "));
                where.append(")");
                condition = " AND ";
            }
            if (commands[i].contains(" like ")) {
                where.append(condition);
                where.append(commands[i].replaceAll(" like \\*", " LIKE CONCAT('%', '"));
                where.append("', '%')");
                condition = " AND ";
            }
        }
        return where.toString().replaceAll("\\*", "");
    }
}

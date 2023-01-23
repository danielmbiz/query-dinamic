package com.example.querydinamic.services;

import com.example.querydinamic.criteries.BirthChildFilterParam;
import com.example.querydinamic.criteries.SearchCriteria;
import com.example.querydinamic.entities.BirthChild;
import com.example.querydinamic.repositories.BirthChildCriteriaCustomRepository;
import com.example.querydinamic.repositories.BirthChildCustomRepository;
import com.example.querydinamic.repositories.BirthChildRepository;
import com.example.querydinamic.specifications.BirthChildSpecification;
import com.example.querydinamic.utils.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BirthChildService {

    @Autowired
    private BirthChildRepository birthChildRepository;
    @Autowired
    private BirthChildCustomRepository birthChildCustomRepository;
    @Autowired
    private BirthChildCriteriaCustomRepository birthChildCriteriaCustomRepository;

    public List<BirthChild> findCustom(String name, LocalDate birth, String father, String mon, String city) {
        return birthChildCustomRepository.findCustom(name, birth, father, mon, city);
    }

    public List<BirthChild> findCustomFilter(String filter) {
        return birthChildCustomRepository.findCustomFilter(filter);
    }

    public List<BirthChild> findCriteria(BirthChildFilterParam params) {
        return birthChildCriteriaCustomRepository.findCriteria(params);
    }

    public List<BirthChild> findAll(String name, LocalDate birth, String father, String mon, String city) {
        Example<BirthChild> query = QueryBuilder.makeQuery(
                BirthChild.builder()
                        .name(name)
                        .birth(birth)
                        .father(father)
                        .mon(mon)
                        .city(city)
                        .build());
        return birthChildRepository.findAll(query);
    }

    public List<BirthChild> findAllCriteria(String filter) {
        List<Specification<BirthChild>> specs = new ArrayList<>();
        String[] wheres = filter.split(";");
        List<SearchCriteria> params = mountCondition(wheres);

        for (SearchCriteria searchCriteria : params) {
            specs.add(new BirthChildSpecification(searchCriteria));
        }

        Specification<BirthChild> specification = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            specification = Specification.where(specification).and(specs.get(i));
        }

        return birthChildRepository.findAll(specification);
    }

    private List<SearchCriteria> mountCondition(String[] wheres) {
        List<SearchCriteria> params = new ArrayList<>();
        for (int i = 0; i <= wheres.length - 1; i++) {
            if (wheres[i].contains(" eq ") || wheres[i].contains(" == ")) {
                String[] commands = wheres[i].split("eq|==");
                String attribute = commands[0].trim();
                String value = commands[1].trim();
                params.add(new SearchCriteria(attribute, "eq", value));
            }

            if (wheres[i].contains(" lt ") || wheres[i].contains(" <: ")) {
                String[] commands = wheres[i].split("lt|<:");
                String attribute = commands[0].trim();
                if (isValidDate(commands[1].trim())) {
                    LocalDate value = LocalDate.parse(commands[1].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    params.add(new SearchCriteria(attribute, "lt", value));
                }
                if (isValidLong(commands[1].trim())) {
                    Long value = Long.parseLong(commands[1].trim());
                    params.add(new SearchCriteria(attribute, "lt", value));
                }
            }

            if (wheres[i].contains(" gt ") || wheres[i].contains(" >: ")) {
                String[] commands = wheres[i].split("gt|>:");
                String attribute = commands[0].trim();
                if (isValidDate(commands[1].trim())) {
                    LocalDate value = LocalDate.parse(commands[1].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    params.add(new SearchCriteria(attribute, "gt", value));
                }
                if (isValidLong(commands[1].trim())) {
                    Long value = Long.parseLong(commands[1].trim());
                    params.add(new SearchCriteria(attribute, "gt", value));
                }
            }

            if (wheres[i].contains(" le ") || wheres[i].contains(" <= ")) {
                String[] commands = wheres[i].split("le|<=");
                String attribute = commands[0].trim();
                if (isValidDate(commands[1].trim())) {
                    LocalDate value = LocalDate.parse(commands[1].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    params.add(new SearchCriteria(attribute, "le", value));
                }
                if (isValidLong(commands[1].trim())) {
                    Long value = Long.parseLong(commands[1].trim());
                    params.add(new SearchCriteria(attribute, "le", value));
                }
            }

            if (wheres[i].contains(" ge ") || wheres[i].contains(" >= ")) {
                String[] commands = wheres[i].split("ge|>=");
                String attribute = commands[0].trim();
                if (isValidDate(commands[1].trim())) {
                    LocalDate value = LocalDate.parse(commands[1].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    params.add(new SearchCriteria(attribute, "ge", value));
                }
                if (isValidLong(commands[1].trim())) {
                    Long value = Long.parseLong(commands[1].trim());
                    params.add(new SearchCriteria(attribute, "ge", value));
                }
            }

            if (wheres[i].contains(" in ")) {
                List<String> listIn = new ArrayList<>();
                String[] commands = wheres[i].split("in");
                String attribute = commands[0].trim();
                String[] valueList = commands[1].split(",");
                for (int x = 0; x <= valueList.length - 1; x++) {
                    listIn.add(valueList[x].trim());
                }
                params.add(new SearchCriteria(attribute, "in", listIn));
            }
            if (wheres[i].contains(" like ")) {
                String[] commands = wheres[i].split("like");
                String attribute = commands[0].trim();
                String value = commands[1].trim();
                params.add(new SearchCriteria(attribute, "like", value));
            }
            if (wheres[i].contains(" not ")) {
                List<String> listIn = new ArrayList<>();
                String[] commands = wheres[i].split("not");
                String attribute = commands[0].trim();
                String[] valueList = commands[1].split(",");
                for (int x = 0; x <= valueList.length - 1; x++) {
                    listIn.add(valueList[x].trim());
                }
                params.add(new SearchCriteria(attribute, "not", listIn));
            }
        }
        return params;
    }

    private boolean isValidLong(String doubleString) {
        try {
            Long.parseLong(doubleString);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidDate(String dateString) {
        try {
            LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

}

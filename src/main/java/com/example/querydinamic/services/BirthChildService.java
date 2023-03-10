package com.example.querydinamic.services;

import com.example.querydinamic.criteries.SearchCriteria;
import com.example.querydinamic.entities.BirthChild;
import com.example.querydinamic.repositories.BirthChildRepository;
import com.example.querydinamic.repositories.specifications.BirthChildSpecification;
import com.example.querydinamic.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BirthChildService {

    @Autowired
    private BirthChildRepository birthChildRepository;

    public Page<BirthChild> findAllCriteria(String filter, Pageable pageable) {
        validEmpty(filter);

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

        return birthChildRepository.findAll(specification, pageable);
    }

    private void validEmpty(String filter) {
        if ((filter == null) || (filter.isEmpty())) {
            throw new ValidationException("Nenhuma condição encontrada");
        }
    }


    private List<SearchCriteria> mountCondition(String[] wheres) {
        List<SearchCriteria> params = new ArrayList<>();
        for (int i = 0; i <= wheres.length - 1; i++) {

            validateCondition(wheres[i]);

            if (wheres[i].contains(" eq ") || wheres[i].contains(" == ")) {
                String[] commands = wheres[i].split("eq|==");
                String attribute = commands[0].trim();
                validAttribue(attribute);
                String value = commands[1].trim();
                params.add(new SearchCriteria(attribute, "eq", value));
            }

            if (wheres[i].contains(" lt ") || wheres[i].contains(" <: ")) {
                String[] commands = wheres[i].split("lt|<:");
                String attribute = commands[0].trim();
                validAttribue(attribute);
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
                validAttribue(attribute);
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
                validAttribue(attribute);
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
                validAttribue(attribute);
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
                validAttribue(attribute);
                String[] valueList = commands[1].split(",");
                for (int x = 0; x <= valueList.length - 1; x++) {
                    listIn.add(valueList[x].trim().toUpperCase());
                }
                params.add(new SearchCriteria(attribute, "in", listIn));
            }
            if (wheres[i].contains(" like ")) {
                String[] commands = wheres[i].split("like");
                String attribute = commands[0].trim();
                String value = commands[1].trim().replaceAll("\\*", "%");
                params.add(new SearchCriteria(attribute, "like", value));
            }
            if (wheres[i].contains(" not ")) {
                List<String> listIn = new ArrayList<>();
                String[] commands = wheres[i].split("not");
                String attribute = commands[0].trim();
                validAttribue(attribute);
                String[] valueList = commands[1].split(",");
                for (int x = 0; x <= valueList.length - 1; x++) {
                    listIn.add(valueList[x].trim().toUpperCase());
                }
                params.add(new SearchCriteria(attribute, "not", listIn));
            }
        }
        return params;
    }

    private void validateCondition(String conditions) {
        if ((!conditions.contains(" eq ")) &&
                (!conditions.contains(" == ")) &&
                (!conditions.contains(" lt ")) &&
                (!conditions.contains(" <: ")) &&
                (!conditions.contains(" gt ")) &&
                (!conditions.contains(" >: ")) &&
                (!conditions.contains(" le ")) &&
                (!conditions.contains(" <= ")) &&
                (!conditions.contains(" ge ")) &&
                (!conditions.contains(" >= ")) &&
                (!conditions.contains(" in ")) &&
                (!conditions.contains(" like ")) &&
                (!conditions.contains(" not "))
        ) {
            throw new ValidationException("Condição não existe -> " + conditions);
        }
    }

    private void validAttribue(String attribute) {
        boolean exists = false;
        Field[] fields = BirthChild.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals(attribute)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            throw new ValidationException("Campo não existe -> " + attribute);
        }

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

package com.example.querydinamic.utils;

import com.example.querydinamic.entities.BirthChild;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

public class QueryBuilder {
    public static Example<BirthChild> makeQuery(BirthChild birthChild) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase().withIgnoreNullValues();
        return Example.of(birthChild, exampleMatcher);
    }
}

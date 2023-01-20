package com.example.querydinamic.services;

import com.example.querydinamic.criteries.BirthChildFilterParam;
import com.example.querydinamic.entities.BirthChild;
import com.example.querydinamic.repositories.BirthChildCriteriaCustomRepository;
import com.example.querydinamic.repositories.BirthChildCustomRepository;
import com.example.querydinamic.repositories.BirthChildRepository;
import com.example.querydinamic.utils.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

}

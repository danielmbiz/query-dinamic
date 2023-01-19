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

import java.util.List;

@Service
public class BirthChildService {

    @Autowired
    private BirthChildRepository birthChildRepository;
    @Autowired
    private BirthChildCustomRepository birthChildCustomRepository;
    @Autowired
    private BirthChildCriteriaCustomRepository birthChildCriteriaCustomRepository;

    public List<BirthChild> findCustom(Long id, String name, String city) {
        return birthChildCustomRepository.findCustom(id, name, city);
    }

    public List<BirthChild> findCriteria(BirthChildFilterParam params) {
        return birthChildCriteriaCustomRepository.getWithFilter(params);
    }

    public List<BirthChild> findAll(String name, String city) {
        Example<BirthChild> query = QueryBuilder.makeQuery(
                BirthChild.builder()
                        .name(name)
                        .city(city)
                        .build());
        return birthChildRepository.findAll(query);
    }

}

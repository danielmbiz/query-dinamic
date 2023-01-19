package com.example.querydinamic.services;

import com.example.querydinamic.criteries.BirthChildFilterParam;
import com.example.querydinamic.entities.BirthChild;
import com.example.querydinamic.repositories.BirthChildCriteriaCustomRepository;
import com.example.querydinamic.repositories.BirthChildCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BirthChildService {

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

}

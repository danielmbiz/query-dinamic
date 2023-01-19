package com.example.querydinamic.repositories;

import com.example.querydinamic.entities.BirthChild;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BirthChildRepository extends JpaRepository<BirthChild, Long> {
    @Override
    <S extends BirthChild> List<S> findAll(Example<S> example);
}

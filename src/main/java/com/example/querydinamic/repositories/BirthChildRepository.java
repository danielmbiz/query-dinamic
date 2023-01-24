package com.example.querydinamic.repositories;

import com.example.querydinamic.entities.BirthChild;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BirthChildRepository extends JpaRepository<BirthChild, Long>, JpaSpecificationExecutor<BirthChild> {

}

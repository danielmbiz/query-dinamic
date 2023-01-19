package com.example.querydinamic.repositories;

import com.example.querydinamic.entities.BirthChild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BirthChildRepository extends JpaRepository<BirthChild, Long>, JpaSpecificationExecutor<BirthChild> {

}

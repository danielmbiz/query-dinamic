package com.example.querydinamic.controllers;

import com.example.querydinamic.criteries.BirthChildFilterParam;
import com.example.querydinamic.services.BirthChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/api")
public class BirthChildController {
    @Autowired
    private BirthChildService birthChildService;

    @GetMapping
    public ResponseEntity<?> findCustom(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String city) {
        return ResponseEntity.ok(birthChildService.findCustom(id, name, city));
    }

    @GetMapping(value = "/criteria")
    public ResponseEntity<?> findCriteria(BirthChildFilterParam params) {
        return ResponseEntity.ok(birthChildService.findCriteria(params));
    }

    @GetMapping(value = "/example")
    public ResponseEntity<?> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String city) {
        return ResponseEntity.ok(birthChildService.findAll(name, city));
    }

}

package com.example.querydinamic.controllers;

import com.example.querydinamic.services.BirthChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping(value = "/api")
public class BirthChildController {
    @Autowired
    private BirthChildService birthChildService;

    @GetMapping(value = "/example")
    public ResponseEntity<?> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) LocalDate birth,
            @RequestParam(required = false) String father,
            @RequestParam(required = false) String mon,
            @RequestParam(required = false) String city) {
        return ResponseEntity.ok(birthChildService.findAll(name, birth, father, mon, city));
    }

    @GetMapping(value = "/criteria")
    public ResponseEntity<?> findAll(@RequestParam(required = false) String filter) {
        return ResponseEntity.ok(birthChildService.findAllCriteria(filter));
    }

}

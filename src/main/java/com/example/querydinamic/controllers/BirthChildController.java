package com.example.querydinamic.controllers;

import com.example.querydinamic.dto.PageInfoDTO;
import com.example.querydinamic.dto.ResponsePageHandler;
import com.example.querydinamic.services.BirthChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.stream.Collectors;


@Controller
@RequestMapping(value = "/api")
public class BirthChildController {
    @Autowired
    private BirthChildService birthChildService;

    @GetMapping(value = "/criteria")
    public ResponseEntity<?> findAll(@RequestParam(required = false) String filter, Pageable pageable) {
        var response = birthChildService.findAllCriteria(filter, pageable);
        var pageInfo = PageInfoDTO.builder()
                .current(response.getNumber())
                .last(response.getTotalPages())
                .size(response.getSize())
                .count(response.getTotalElements())
                .build();
        var listResponse = response
                .stream()
                .collect(Collectors.toList());
        return ResponsePageHandler.responseBuilder(
                HttpStatus.OK,
                200,
                listResponse,
                pageInfo);
    }

}

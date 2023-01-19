package com.example.querydinamic.criteries;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BirthChildFilterParam {
    private Long id;
    private String name;
    private LocalDate birth;
    private String father;
    private String mon;
    private String city;
}

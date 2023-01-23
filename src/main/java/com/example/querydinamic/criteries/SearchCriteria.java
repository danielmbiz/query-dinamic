package com.example.querydinamic.criteries;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Builder
public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;

}

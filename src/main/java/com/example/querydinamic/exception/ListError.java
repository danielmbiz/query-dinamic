package com.example.querydinamic.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class ListError {
    private String status;
    private Integer code;
    private final List<ErrorObject> messages;
    private List<String> result;
}
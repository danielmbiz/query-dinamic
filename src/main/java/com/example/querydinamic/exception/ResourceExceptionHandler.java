package com.example.querydinamic.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {

    List<String> listClear = new ArrayList<>();

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<StandardError> validationException(ValidationException e, HttpServletRequest request) {
        List<String> listError = new ArrayList<>();
        listError.add((e.getMessage()));
        HttpStatus status = HttpStatus.NOT_FOUND;
        var err = StandardError.builder()
                .status("NOT FOUND")
                .code(status.value())
                .message(listError)
                .result(listClear)
                .build();
        return ResponseEntity.status(status).body(err);
    }
}

package com.example.querydinamic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ResponsePageDTO {
    private String status;
    private Integer code;
    private List<String> messages;
    private Object result;
    @JsonProperty("page-info")
    private PageInfoDTO pageInfo;

}

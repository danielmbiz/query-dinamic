package com.example.querydinamic.exception;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StandardError {
	private String status;
	private Integer code;
	private List<String> message;
	private List<String> result;

}
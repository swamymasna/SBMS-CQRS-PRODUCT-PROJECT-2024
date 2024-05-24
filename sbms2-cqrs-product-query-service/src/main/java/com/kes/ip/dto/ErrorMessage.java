package com.kes.ip.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ErrorMessage {

	private String message;
	private HttpStatus status;
	private Integer statusCode;
	private String timeStamp;
	private String path;
}

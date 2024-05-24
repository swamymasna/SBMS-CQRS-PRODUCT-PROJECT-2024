package com.kes.ip.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.kes.ip.dto.ErrorMessage;
import com.kes.ip.exception.ProductServiceBusinessException;
import com.kes.ip.exception.ResourceNotFoundException;

@RestControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(value = ResourceNotFoundException.class)
	public ResponseEntity<ErrorMessage> handleResourceNotFoundException(ResourceNotFoundException exception,
			WebRequest webRequest) {

		ErrorMessage errorMessage = ErrorMessage.builder().message(exception.getMessage()).status(HttpStatus.NOT_FOUND)
				.statusCode(HttpStatus.NOT_FOUND.value()).timeStamp(LocalDateTime.now().toString())
				.path(webRequest.getDescription(false)).build();

		return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = ProductServiceBusinessException.class)
	public ResponseEntity<ErrorMessage> handleProductServiceBusinessException(ProductServiceBusinessException exception,
			WebRequest webRequest) {

		ErrorMessage errorMessage = ErrorMessage.builder().message(exception.getMessage())
				.status(HttpStatus.INTERNAL_SERVER_ERROR).statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.timeStamp(LocalDateTime.now().toString()).path(webRequest.getDescription(false)).build();

		return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

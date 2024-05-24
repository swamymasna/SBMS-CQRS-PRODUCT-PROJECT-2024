package com.kes.ip.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kes.ip.dto.ProductEvent;
import com.kes.ip.dto.ProductResponseDto;
import com.kes.ip.service.ProductCommandService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductCommandController {

	private ProductCommandService productCommandService;

	@PostMapping
	public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductEvent productEvent) {
		return new ResponseEntity<>(productCommandService.saveProduct(productEvent), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable("id") Integer productId,
			@RequestBody ProductEvent productEvent) {
		return new ResponseEntity<>(productCommandService.updateProduct(productId, productEvent), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable("id") Integer productId) {
		return new ResponseEntity<>(productCommandService.deleteProduct(productId), HttpStatus.OK);
	}

}

package com.kes.ip.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kes.ip.dto.ProductResponseDto;
import com.kes.ip.service.ProductQueryService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductQueryController {

	private ProductQueryService productQueryService;

	@GetMapping
	public ResponseEntity<List<ProductResponseDto>> fetchAllProducts() {
		return new ResponseEntity<>(productQueryService.getAllProducts(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductResponseDto> fetchProductById(@PathVariable("id") Integer productId) {
		return new ResponseEntity<>(productQueryService.getProductById(productId), HttpStatus.OK);
	}

}

package com.kes.ip.service;

import java.util.List;

import com.kes.ip.dto.ProductResponseDto;

public interface ProductQueryService {

	List<ProductResponseDto> getAllProducts();

	ProductResponseDto getProductById(Integer productId);
}

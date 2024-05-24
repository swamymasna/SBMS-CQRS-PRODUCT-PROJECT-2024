package com.kes.ip.service;

import com.kes.ip.dto.ProductEvent;
import com.kes.ip.dto.ProductResponseDto;

public interface ProductCommandService {

	ProductResponseDto saveProduct(ProductEvent productEvent);

	ProductResponseDto updateProduct(Integer productId, ProductEvent productEvent);

	String deleteProduct(Integer productId);
}

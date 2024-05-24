package com.kes.ip.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.kes.ip.dto.ProductEvent;
import com.kes.ip.dto.ProductResponseDto;
import com.kes.ip.entity.Product;
import com.kes.ip.exception.ProductServiceBusinessException;
import com.kes.ip.exception.ResourceNotFoundException;
import com.kes.ip.repository.ProductRepository;
import com.kes.ip.service.ProductQueryService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductQueryServiceImpl implements ProductQueryService {

	private ProductRepository productRepository;

	private ModelMapper modelMapper;

	@Override
	public List<ProductResponseDto> getAllProducts() {

		List<ProductResponseDto> productsList = null;

		List<Product> products = null;

		try {

			products = productRepository.findAll();

			if (!products.isEmpty()) {
				productsList = products.stream().map(product -> modelMapper.map(product, ProductResponseDto.class))
						.collect(Collectors.toList());
			} else {
				productsList = Collections.emptyList();
			}

		} catch (Exception ex) {
			throw new ProductServiceBusinessException(
					String.format("Exception Occured While Fetching Products from the Database", ex));
		}

		return productsList;
	}

	@Override
	public ProductResponseDto getProductById(Integer productId) {

		Product product = productRepository.findById(productId).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Product Not Found With Id : %s", productId)));

		return modelMapper.map(product, ProductResponseDto.class);
	}

	@KafkaListener(topics = "${kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
	public void readProductEvent(ProductEvent productEvent) {

		String eventType = productEvent.getEventType();

		Product product = productEvent.getProduct();

		if (eventType.equals("CREATE_PRODUCT")) {

			productRepository.save(product);
		}

		if (eventType.equals("UPDATE_PRODUCT")) {
			Product existingProduct = productRepository.findById(product.getId()).get();

			existingProduct.setProductName(product.getProductName());
			existingProduct.setProductCost(product.getProductCost());
			existingProduct.setProductDescription(product.getProductDescription());

			productRepository.save(existingProduct);
		}

		if (eventType.equals("DELETE_PRODUCT")) {
			Product existingProduct = productRepository.findById(product.getId()).get();
			productRepository.delete(existingProduct);
		}

	}

}

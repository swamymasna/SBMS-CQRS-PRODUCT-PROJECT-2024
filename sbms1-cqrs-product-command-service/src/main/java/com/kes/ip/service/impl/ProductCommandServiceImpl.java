package com.kes.ip.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.kes.ip.dto.ProductEvent;
import com.kes.ip.dto.ProductResponseDto;
import com.kes.ip.entity.Product;
import com.kes.ip.exception.ProductServiceBusinessException;
import com.kes.ip.exception.ResourceNotFoundException;
import com.kes.ip.repository.ProductRepository;
import com.kes.ip.service.ProductCommandService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductCommandServiceImpl implements ProductCommandService {

	private ProductRepository productRepository;

	private ModelMapper modelMapper;

	private KafkaTemplate<String, ProductEvent> kafkaTemplate;

	@Override
	public ProductResponseDto saveProduct(ProductEvent productEvent) {

		ProductResponseDto productResponseDto = null;

		Product product = null;

		try {

			product = productEvent.getProduct();

			product = productRepository.save(product);

			ProductEvent kafkaEvent = new ProductEvent("CREATE_PRODUCT", product);

			kafkaTemplate.send("product-command-topic01", kafkaEvent);

			if (product.getId() != null) {
				productResponseDto = modelMapper.map(product, ProductResponseDto.class);
			} else {
				productResponseDto = null;
			}

		} catch (Exception ex) {
			throw new ProductServiceBusinessException(
					String.format("Exception Occured While Saving Product Into the Database..?", ex));
		}

		return productResponseDto;
	}

	@Override
	public ProductResponseDto updateProduct(Integer productId, ProductEvent productEvent) {

		ProductResponseDto productResponseDto = null;

		Product product = null;

		Product existingProduct = productRepository.findById(productId).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Product Not Found With Id : %s", productId)));

		try {
			product = productEvent.getProduct();

			existingProduct.setProductName(product.getProductName());
			existingProduct.setProductCost(product.getProductCost());
			existingProduct.setProductDescription(product.getProductDescription());

			product = productRepository.save(existingProduct);

			ProductEvent kafkaEvent = new ProductEvent("UPDATE_PRODUCT", product);

			kafkaTemplate.send("product-command-topic01", kafkaEvent);

			if (product.getId() != null) {
				productResponseDto = modelMapper.map(product, ProductResponseDto.class);
			} else {
				productResponseDto = null;
			}

		} catch (Exception ex) {
			throw new ProductServiceBusinessException(
					String.format("Exception Occured While Updating Product Into the Database..?", ex));
		}

		return productResponseDto;
	}

	@Override
	public String deleteProduct(Integer productId) {

		Product product = productRepository.findById(productId).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Product Not Found With Id : %s", productId)));

		try {
			productRepository.delete(product);

			ProductEvent kafkaEvent = new ProductEvent("DELETE_PRODUCT", product);

			kafkaTemplate.send("product-command-topic01", kafkaEvent);

		} catch (Exception ex) {
			throw new ProductServiceBusinessException(
					String.format("Exception Occured While Deleting Product Into the Database..?", ex));

		}

		return "Product Deleted With Id : " + productId;
	}

}

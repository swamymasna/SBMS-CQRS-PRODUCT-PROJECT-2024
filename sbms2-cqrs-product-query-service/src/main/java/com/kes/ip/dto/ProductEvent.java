package com.kes.ip.dto;

import com.kes.ip.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductEvent {

	private String eventType;
	private Product product;
}

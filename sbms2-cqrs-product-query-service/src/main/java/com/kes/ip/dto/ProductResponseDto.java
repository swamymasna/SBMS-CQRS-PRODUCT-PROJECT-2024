package com.kes.ip.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductResponseDto {

	private Integer id;

	private String productName;

	private Double productCost;

	private String productDescription;
}

package com.kes.ip.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kes.ip.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}

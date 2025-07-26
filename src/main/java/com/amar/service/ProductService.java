package com.amar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.amar.entity.Product;
import com.amar.entity.ProductSpecification;
import com.amar.repository.ProductRepository;

@Service
public class ProductService {

	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public Product createProduct(Product product) {
		return productRepository.save(product);
	}

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public Optional<Product> getProductById(Long id) {
		return productRepository.findById(id);
	}

	public Product updateProduct(Long id, Product updatedProduct) {
		return productRepository.findById(id).map(p -> {
			p.setName(updatedProduct.getName());
			p.setDescription(updatedProduct.getDescription());
			p.setPrice(updatedProduct.getPrice());
			p.setQuantity(updatedProduct.getQuantity());
			return productRepository.save(p);
		}).orElseThrow(() -> new RuntimeException("Product not found"));
	}

	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}

	public Page<Product> search(String name, Double minPrice, Double maxPrice, Pageable pageable) {
		Specification<Product> spec = Specification.where(ProductSpecification.nameContains(name))
				.and(ProductSpecification.priceGreaterThanOrEqual(minPrice))
				.and(ProductSpecification.priceLessThanOrEqual(maxPrice));

		return productRepository.findAll(spec, pageable);
	}

	public List<Product> filterProducts(Double minPrice, Double maxPrice, Boolean hasDiscount, Integer quantityGt,
			String searchText, String name) {
		return productRepository.filterProducts(minPrice, maxPrice, hasDiscount, quantityGt, searchText, name);
	}

}

package dev.damian.stockcontrol.service;

import dev.damian.stockcontrol.model.Product;
import dev.damian.stockcontrol.model.dto.ProductDTO;
import dev.damian.stockcontrol.repository.ProductRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;

	public List<Product> retrieveAll() {
		return productRepository.findAll();
	}

	public Product create(ProductDTO productDto) {

		productRepository.findByCode(productDto.getCode()).ifPresent(p -> {
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					String.format("Product '%s' already exists!", productDto.getCode()));
		});

		Product product = new Product(productDto.getCode(), productDto.getDescription());

		return productRepository.save(product);
	}

	public Product retrieveByCode(String code) {
		return productRepository.findByCode(code).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
				String.format("Product '%s' not found!", code)));
	}

}

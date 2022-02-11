package dev.damian.stockcontrol.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.damian.stockcontrol.model.Product;
import dev.damian.stockcontrol.model.dto.ProductDTO;
import dev.damian.stockcontrol.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("products")
@Tag(name = "Products", description = "Products management")
public class ProductController {

	@Autowired
	ProductService productService;

	@GetMapping
	@Operation(summary = "Retrieves all products")
	public List<Product> findAll() {
		return productService.retrieveAll();
	}

	@PostMapping
	@Operation(summary = "Creates a product")
	public Product create(@RequestBody ProductDTO product) {
		return productService.create(product);
	}

}

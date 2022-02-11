package dev.damian.stockcontrol.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.damian.stockcontrol.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	public Optional<Product> findByCode(String code);

}

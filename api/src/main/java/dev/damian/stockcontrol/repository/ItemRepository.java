package dev.damian.stockcontrol.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.damian.stockcontrol.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

	public List<Item> findByLocationAndDeposit_Code(String location, String code);

	public List<Item> findByProduct_CodeAndDeposit_Code(String productCode, String depositCode);
}

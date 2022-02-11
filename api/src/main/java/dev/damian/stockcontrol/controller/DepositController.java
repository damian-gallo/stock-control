package dev.damian.stockcontrol.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.damian.stockcontrol.model.Deposit;
import dev.damian.stockcontrol.model.Item;
import dev.damian.stockcontrol.model.dto.DepositDTO;
import dev.damian.stockcontrol.model.dto.ItemDTO;
import dev.damian.stockcontrol.service.DepositService;
import dev.damian.stockcontrol.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("deposits")
@Tag(name = "Deposits", description = "Deposits management")
public class DepositController {

	@Autowired
	DepositService depositService;

	@Autowired
	ItemService itemService;

	@GetMapping
	@Operation(summary = "Retrieves all deposits")
	public List<Deposit> findAll() {
		return depositService.retrieveAll();
	}

	@PostMapping
	@Operation(summary = "Creates a deposit")
	public Deposit create(@RequestBody DepositDTO deposit) {
		return depositService.create(deposit);
	}

	@GetMapping("{depositCode}")
	@Operation(summary = "Retrieves a deposit")
	public Deposit findByDepositCode(@PathVariable String depositCode) {
		return depositService.retrieveByCode(depositCode);
	}

	@GetMapping("{depositCode}/products/{productCode}")
	@Operation(summary = "Retrieves all items containing the specified product code in a deposit")
	public List<Item> findItemsByProductCodeAndDepositCode(@PathVariable String depositCode,
			@PathVariable String productCode) {
		return itemService.retrieveByProductCodeAndDepositCode(productCode, depositCode);
	}

	@GetMapping("{depositCode}/locations/{location}")
	@Operation(summary = "Retrieves all items at the specified location in a deposit")
	public List<Item> findItemsByLocationAndDepositCode(@PathVariable String depositCode,
			@PathVariable String location) {
		return itemService.retrieveByLocationAndDepositCode(location, depositCode);
	}

	@PostMapping("{depositCode}/locations/{location}/restocking")
	@Operation(summary = "Increases stock of the specified product")
	public Deposit restocking(@PathVariable String depositCode, @PathVariable String location,
			@RequestBody ItemDTO item) {

		return depositService.restock(depositCode, location, item);
	}

	@PostMapping("{depositCode}/locations/{location}/withdrawal")
	@Operation(summary = "Decreases stock of the specified product")
	public Deposit withdrawal(@PathVariable String depositCode, @PathVariable String location,
			@RequestBody ItemDTO item) {

		return depositService.withdraw(depositCode, location, item);
	}

}

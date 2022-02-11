package dev.damian.stockcontrol.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dev.damian.stockcontrol.model.Deposit;
import dev.damian.stockcontrol.model.Item;
import dev.damian.stockcontrol.model.Product;
import dev.damian.stockcontrol.model.dto.DepositDTO;
import dev.damian.stockcontrol.model.dto.ItemDTO;
import dev.damian.stockcontrol.repository.DepositRepository;

@Service
public class DepositService {

	@Autowired
	DepositRepository depositRepository;

	@Autowired
	ProductService productService;

	@Autowired
	ItemService itemService;

	public Deposit withdraw(String depositCode, String location, ItemDTO itemDto) {
		Item item = itemService.retrieveByLocationAndProductCodeAndDepositCode(location, itemDto.getProduct(),
				depositCode);

		if (exceedsMinQuantity(item, itemDto.getQuantity()))
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					String.format("Min quantity exceeded for location '%s'!", location));

		item.setQuantity(item.getQuantity() - itemDto.getQuantity());

		item.getDeposit().getItems().removeIf(i -> i.getQuantity().equals(0));

		return depositRepository.save(item.getDeposit());
	}

	public Deposit restock(String depositCode, String location, ItemDTO itemDto) {
		if (!isValidLocation(location))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					String.format("Location '%s' has an invalid format!", location));

		if (itemDto.getQuantity() < 1)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity must be bigger than zero!");

		List<Item> items = itemService.retrieveByLocationAndDepositCode(location, depositCode);

		if (exceedsMaxProducts(items, itemDto.getProduct()))
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					String.format("Max products exceeded for location '%s'!", location));

		if (exceedsMaxQuantity(items, itemDto.getQuantity()))
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					String.format("Max quantity exceeded for location '%s'!", location));

		Deposit deposit = depositRepository.findByCode(depositCode)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						String.format("Deposit '%s' not found!", depositCode)));

		Product product = productService.retrieveByCode(itemDto.getProduct());

		Item item = items.stream().filter(i -> i.getProduct().getCode().equals(itemDto.getProduct())).findFirst()
				.orElse(new Item(product, location, deposit));

		item.setQuantity(item.getQuantity() + itemDto.getQuantity());

		if (!depositContainsItem(deposit, item))
			deposit.getItems().add(item);

		return depositRepository.save(deposit);

	}

	public Deposit create(DepositDTO depositDto) {
		depositRepository.findByCode(depositDto.getCode()).ifPresent(d -> {
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					String.format("Deposit '%s' already exists!", depositDto.getCode()));
		});

		Deposit deposit = new Deposit(depositDto.getCode(), depositDto.getDescription());

		return depositRepository.save(deposit);
	}

	public List<Deposit> retrieveAll() {
		return depositRepository.findAll();
	}

	public Deposit retrieveByCode(String code) {
		return depositRepository.findByCode(code).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
				String.format("Deposit '%s' not found!", code)));
	}

	public Boolean isValidLocation(String location) {
		String[] parts = location.split("-");

		if (parts.length != 4)
			return false;

		for (String part : parts) {
			if (part.length() != 2)
				return false;
		}

		return true;
	}

	public Boolean exceedsMaxProducts(List<Item> items, String product) {
		Boolean productIsInItems = items.stream().anyMatch(item -> item.getProduct().getCode().equals(product));
		if (!productIsInItems && items.size() >= 3)
			return true;

		return false;
	}

	public Boolean exceedsMaxQuantity(List<Item> items, int quantity) {
		int itemsQuantity = items.stream().reduce(0, (accumulated, item) -> accumulated + item.getQuantity(),
				Integer::sum);
		return itemsQuantity + quantity > 100;
	}

	public Boolean exceedsMinQuantity(Item item, int quantity) {
		return item.getQuantity() - quantity < 0;
	}

	public Boolean depositContainsItem(Deposit deposit, Item item) {
		return deposit.getItems().indexOf(item) >= 0;
	}

}

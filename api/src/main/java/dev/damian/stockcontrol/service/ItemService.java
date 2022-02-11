package dev.damian.stockcontrol.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dev.damian.stockcontrol.model.Item;
import dev.damian.stockcontrol.repository.ItemRepository;

@Service
public class ItemService {

	@Autowired
	ItemRepository itemRepository;

	public List<Item> retrieveByLocationAndDepositCode(String location, String depositCode) {
		return itemRepository.findByLocationAndDeposit_Code(location, depositCode);
	}

	public List<Item> retrieveByProductCodeAndDepositCode(String productCode, String depositCode) {
		return itemRepository.findByProduct_CodeAndDeposit_Code(productCode, depositCode);
	}

	public Item retrieveByLocationAndProductCodeAndDepositCode(String location, String productCode,
			String depositCode) {
		return itemRepository.findByLocationAndProduct_CodeAndDeposit_Code(location, productCode, depositCode)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						String.format("Product '%s' in location '%s' not found!", productCode, location)));
	}

}

package dev.damian.stockcontrol.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}

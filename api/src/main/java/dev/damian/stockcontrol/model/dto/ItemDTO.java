package dev.damian.stockcontrol.model.dto;

public class ItemDTO {

	private String product;
	private int quantity;

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}

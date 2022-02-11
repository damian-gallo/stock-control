package dev.damian.stockcontrol.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	private Product product;

	@Column
	private Integer quantity;

	@Column
	private String location;

	@ManyToOne
	@JsonBackReference
	private Deposit deposit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Deposit getDeposit() {
		return deposit;
	}

	public void setDeposit(Deposit deposit) {
		this.deposit = deposit;
	}

	public Item() {
	}

	public Item(Product product, String location, Deposit deposit) {
		super();
		this.product = product;
		this.quantity = 0;
		this.location = location;
		this.deposit = deposit;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", product=" + product + ", quantity=" + quantity + ", location=" + location + "]";
	}
}

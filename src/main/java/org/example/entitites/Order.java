package org.example.entitites;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Order {
	private long id;
	private String status;
	private LocalDate orderDate;
	private LocalDate deliveryDate;
	private List<Product> products;
	private Customer customer;

	public Order(Customer customer) {
		Random rndm = new Random();
		this.id = rndm.nextLong();
		this.customer = customer;
		this.status = "Just created";
		this.orderDate = LocalDate.now();
		this.deliveryDate = LocalDate.now().plusWeeks(1);
		this.products = new ArrayList<>();
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", status=" + status + ", orderDate=" + orderDate + ", deliveryDate=" + deliveryDate
				+ ", products=" + products + ", customer=" + customer + "]";
	}

	public double getTotal() {
		return this.products.stream().mapToDouble(Product::getPrice).sum();
	}

	public long getId() {
		return id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void addProduct(Product p) {
		products.add(p);
	}

	public Customer getCustomer() {
		return customer;
	}

}

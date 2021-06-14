package com.cheikh.springdemo.dao;

import java.util.List;

import com.cheikh.springdemo.entity.Customer;

public interface CustomerDAO {

	
	public List<Customer> getCustomers();

	public void saveCustomer(Customer theCustomer);

	public Customer getCustomers(int theId);

	public void deleteCustomer(int theId);
}

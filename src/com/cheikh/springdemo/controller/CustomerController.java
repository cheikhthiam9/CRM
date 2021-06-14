package com.cheikh.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cheikh.springdemo.entity.Customer;
import com.cheikh.springdemo.service.CustomerService;


//Reminder: 
//@Controller just specifies to Spring that this bean is a Controller
//@RequetMapping specifies that we're mapping a specific web request path(/customer)
//to a specific method or class
//@RequestMapping will handle all types of requests 

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	// need to inject the customer Service 
	// Reminder: When we inject a dependency, spring will scan for a Component 
	// that implements the interface and it will perform it here
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/list")
	public String listCustomer(Model theModel) 
	{
		
		//get the customers from the service 
		List<Customer> theCustomers = customerService.getCustomers();

		
		// add the customers to the model 
		// the attribute name 'customers' needs to match the 
		theModel.addAttribute("customers", theCustomers);
		
		//send data from the model to jsp page "list-customers"
		return "list-customers";
	}
	
	
	
	
	
	@GetMapping("/showFormForAdd") 
	public String showFormForAdd(Model theModel) 
	
	{
		//create model attribute to bind form data 
		Customer theCustomer = new Customer();
		
		theModel.addAttribute("customer", theCustomer);
		return "customer-form";
	}
	
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer theCustomer) 
	{
		
		customerService.saveCustomer(theCustomer);
		
		return "redirect:/customer/list";
	}
	
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int theId,
			Model theModel)
	{
		
		// get the customer from our service  
		Customer theCustomer = customerService.getCustomers(theId);
		
		// set customer as a model attrivute to pre-populate the form 
		theModel.addAttribute("customer", theCustomer);
		
		//send over to our form 
		
		return  "customer-form";
	}
	
	
	@GetMapping("/delete")
	public String deleteCustomer (@RequestParam("customerId") int theId) {
		
		customerService.deleteCustomer(theId);
		
		
		return "redirect:/customer/list"; 
	}
	

}

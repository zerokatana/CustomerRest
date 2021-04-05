package com.johndoe.CustomerRest.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.johndoe.CustomerRest.service.CustomerService;

@RestController
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	/**
	 * Gets a list of all customers
	 * @return
	 */
	@GetMapping("/customers")
	public ResponseEntity<List<CustomerDto>> getCustomers(){
		List<CustomerDto> allCustomers = customerService.listAll();
		return ResponseEntity.ok(allCustomers);
	}
	
	/**
	 * Find customer by id
	 * @param id
	 * @return
	 */
	@GetMapping("/customers/{id}")
	public ResponseEntity<CustomerDto> getCustomer(@PathVariable Integer id) {
	    if ( customerService.customerExists(id) ) {
	    	CustomerDto customer = customerService.get(id);
	        return new ResponseEntity<CustomerDto>(customer, HttpStatus.OK);
	    }
	    
	    return new ResponseEntity<CustomerDto>(HttpStatus.NOT_FOUND);          
	}
	
	/**
	 * Find customers by company
	 * @param company
	 * @return
	 */
	@GetMapping("/customers/company/{company}")
	public ResponseEntity<List<CustomerDto>> getCustomersByCompany(@PathVariable String company) {
		List<CustomerDto> customers = customerService.getCustomerByCompany(company);
		return ResponseEntity.ok(customers);			
	}
	
	/**
	 * Creates new customer
	 * @param customer
	 * @return
	 */
	@PostMapping("/customers")
	public ResponseEntity<CustomerDto> addCustomer(@RequestBody CustomerData customer) {
		CustomerDto createdCustomer = customerService.createCustomer(customer);
		return new ResponseEntity<CustomerDto>(createdCustomer, HttpStatus.OK);
	}
		
	/**
	 * Updates an existing customer and checks if the customer exists
	 * @param customer
	 * @param id
	 * @return
	 */
	@PutMapping("/customers/{id}")
	public ResponseEntity<CustomerDto> updateCustomer(@RequestBody CustomerData customer, @PathVariable Integer id) {
		if ( customerService.customerExists(id) ) {
	    	CustomerDto updatedCustomer = customerService.updateCustomer(customer, id);
	        return new ResponseEntity<CustomerDto>(updatedCustomer, HttpStatus.OK);
	    }
	    return new ResponseEntity<CustomerDto>(HttpStatus.NOT_FOUND);	
	}
	
	/**
	 * Deletes customer
	 * @param id
	 * @return
	 */
	@DeleteMapping("/customers/{id}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
		if ( customerService.customerExists(id) ) {
			customerService.delete(id);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	    }
	    
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	

}

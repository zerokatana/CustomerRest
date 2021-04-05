package com.johndoe.CustomerRest.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.johndoe.CustomerRest.repo.Customer;
import com.johndoe.CustomerRest.repo.CustomerRepositoryInterface;
import com.johndoe.CustomerRest.rest.CustomerData;
import com.johndoe.CustomerRest.rest.CustomerDto;



@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepositoryInterface repo;
	
	/**
	 * Finds us all customers, converts them to a stream then maps it
	 * to a customerDto and collects it to a list
	 * @return List of CustomerDto
	 */
	public List<CustomerDto> listAll(){
		return repo.findAll().stream()//converts it to stream
				.map(customer -> mapCustomerDto(customer))
				.collect(Collectors.toList());
	}
	
	/**
	 * Creates a Customer and maps it on a CustomerDto
	 * @param customerData
	 * @return CustomerDto
	 */
	public CustomerDto createCustomer(CustomerData customerData) {
		Customer newCustomer = mapCustomer(customerData, null);
		return mapCustomerDto(repo.save(newCustomer));
	}
	
	/**
	 * Updates a Customer and maps it on a CustomerDto
	 * @param customerData
	 * @param idParam
	 * @return CustomerDto
	 */
	public CustomerDto updateCustomer(CustomerData customerData, Integer idParam) {
		Customer customer = mapCustomer(customerData, idParam);
		return mapCustomerDto(repo.save(customer));
	}
	
	/**
	 * Finds us a customer by company, converts it to a stream then maps it
	 * to a customerDto and collects it to a list 
	 * @param company
	 * @return List of CustomerDto
	 */
	public List<CustomerDto> getCustomerByCompany(String company) {
		return repo.findByCompany(company).stream() //converts it to stream
				.map(customer -> mapCustomerDto(customer)) 
				.collect(Collectors.toList());
	}
	
	public void delete(Integer id) {
		repo.deleteById(id);
	}

	/**
	 * Returns customer object if exists else throws NoSuchElementException. 
	 * @param id
	 * @return CustomerDto
	 */
	public CustomerDto get(Integer id) {		
		return mapCustomerDto(repo.findById(id).get());
	}
	
	/**
	 * Check if customer exist
	 * @param id the id of customer
	 * @return true if optional not empty and customer is present
	 */
	public boolean customerExists(Integer id) {
		Optional<Customer> customer = repo.findById(id);
		if ( customer.isPresent() ) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * Mapping CustomerData to Customer
	 * @param customerData
	 * @param id
	 * @return customer
	 */
	public static Customer mapCustomer(CustomerData customerData, Integer id) {
		Customer customer = new Customer();
		customer.setId(id);
		customer.setName(customerData.getName());
		customer.setEmail(customerData.getEmail());
		customer.setCompany(customerData.getCompany());
		return customer;
	}
	
	/**
	 * Mapping Customer to CustomerDto
	 * @param customer
	 * @return
	 */
	public static CustomerDto mapCustomerDto(Customer customer) {
		CustomerDto customerDto = new CustomerDto();
		customerDto.setId(customer.getId());
		customerDto.setName(customer.getName());
		customerDto.setEmail(customer.getEmail());
		customerDto.setCompany(customer.getCompany());
		return customerDto;
	}
	
	
	

}

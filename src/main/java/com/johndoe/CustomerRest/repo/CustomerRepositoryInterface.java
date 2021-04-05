package com.johndoe.CustomerRest.repo;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepositoryInterface extends JpaRepository<Customer, Integer> {
	
	  /**	   
	   * Added method to find customers with the same company
	   * @param company
	   * @return 
	   */
	  List<Customer> findByCompany(String company);

}

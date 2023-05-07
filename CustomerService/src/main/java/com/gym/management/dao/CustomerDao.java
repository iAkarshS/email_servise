package com.gym.management.dao;

import com.gym.management.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Integer>{
	
	Customer findByEmailIdAndPassword(String emailId, String password);
	List<Customer> findByClientId(String clientId);
	List<Customer> findByName(String name);
	List<Customer> findByNameContainingIgnoreCase(String name);
	Customer findByContact(String contact);

}

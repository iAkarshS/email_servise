package com.gym.management.service;

import com.gym.management.entity.Customer;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
public interface CustomerService {
	
	void registerCustomer(Customer customer, MultipartFile customerPic);
	List<Customer> getAllCustomer();
	Customer loginCustomer(String emailId, String password);
	List<Customer> getCustomerByClientId(String clientId);
	List<Customer> getCustomerByName(String name);
	Customer getCustomerByContact(String contact);

}

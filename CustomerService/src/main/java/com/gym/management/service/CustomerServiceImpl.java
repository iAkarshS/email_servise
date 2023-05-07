package com.gym.management.service;

import com.gym.management.dao.CustomerDao;
import com.gym.management.entity.Customer;
import com.gym.management.utility.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private StorageService storageService;
	
	@Override
	public void registerCustomer(Customer customer, MultipartFile customerPic) {
        String customerImageName = storageService.store(customerPic);
        customer.setPic(customerImageName);
		this.customerDao.save(customer);
	}

	@Override
	public List<Customer> getAllCustomer() {
		return this.customerDao.findAll();
	}

	@Override
	public Customer loginCustomer(String emailId, String password) {
		return this.customerDao.findByEmailIdAndPassword(emailId, password);
	}

	@Override
	public List<Customer> getCustomerByClientId(String clientId) {
		return this.customerDao.findByClientId(clientId);
	}

	@Override
	public List<Customer> getCustomerByName(String name) {
		return this.customerDao.findByNameContainingIgnoreCase(name);
	}

	@Override
	public Customer getCustomerByContact(String contact) {
		return this.customerDao.findByContact(contact);
	}

}

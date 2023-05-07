package com.gym.management.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gym.management.dto.Customer;
import com.gym.management.entity.Admin;
import com.gym.management.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("api/admin/")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {
	
	@Autowired
	private AdminService adminService;

	@PostMapping("register")
	public ResponseEntity<?> registerAdmin(Admin admin) {
		System.out.println("recieved request for Register Admin");
		System.out.println(admin);
		
		adminService.registerAdmin(admin);
		
		System.out.println("response sent!!!");
		return ResponseEntity.ok(admin);
		
	}
	
	@PostMapping("login")
	public ResponseEntity<?> loginAdmin(@RequestParam("username") String username, @RequestParam("password") String password) {
		System.out.println("recieved request for login Admin");
		
		Admin admin = adminService.loginAdmin(username, password);
		
		System.out.println("response sent!!!");
		return ResponseEntity.ok(admin);
		
	}
	
	@GetMapping("allcustomer")
	public ResponseEntity<?> getAllCustomer() throws Exception {

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List> customersList = restTemplate.getForEntity("http://localhost:8092/api/customer/allcustomer", List.class);
		List<Customer> customers = customersList.getBody();
		System.out.println(customers);

		return ResponseEntity.ok(customers);
		
	}

	@GetMapping("search/customer/clientId")
	public ResponseEntity getCustomerByClientId(@RequestParam("clientId") String clientId) throws JsonProcessingException {

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List> customersList = restTemplate.getForEntity("http://localhost:8092/api/customer/search/customer/clientId?clientId={clientId}", List.class, clientId);
		List<Customer> customers = customersList.getBody();

		System.out.println(customers);

		return new ResponseEntity(customers, HttpStatus.OK);

	}
	
	@GetMapping("search/customer/name")
	public ResponseEntity getOrdersByOrderId(@RequestParam("customerName") String customerName) throws JsonProcessingException {

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List> customersList = restTemplate.getForEntity("http://localhost:8092/api/customer/search/customer/name?customerName={customerName}", List.class, customerName);
		List<Customer> customers = customersList.getBody();

		System.out.println(customers);
		return new ResponseEntity(customers, HttpStatus.OK);

	}

}

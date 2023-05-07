package com.gym.management.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gym.management.dao.CustomerDao;
import com.gym.management.dto.AddCustomerRequestDto;
import com.gym.management.dto.ChangePasswordDto;
import com.gym.management.dto.UserLoginRequest;
import com.gym.management.entity.Customer;
import com.gym.management.service.CustomerService;
import com.gym.management.utility.Helper;
import com.gym.management.utility.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/customer/")
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private StorageService storageService;

	@Autowired
	private CustomerDao customerDao;
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	@PostMapping("register")
	public ResponseEntity<?> addProduct(AddCustomerRequestDto addCustomerRequestDto) {
		System.out.println("recieved request for Register customer");
		System.out.println(addCustomerRequestDto);
		
		String clientId = Helper.getAlphaNumericId();
		
		Customer customer= AddCustomerRequestDto.toEntity(addCustomerRequestDto);
		customer.setClientId(clientId);
		
		customerService.registerCustomer(customer, addCustomerRequestDto.getPic());

		System.out.println("response sent!!!");
		return ResponseEntity.ok(customer);
		
	}
	

	@PostMapping("login")
	public ResponseEntity<?> loginAdmin(@RequestBody UserLoginRequest loginRequest) {
		System.out.println("recieved request for login customer");
		
		System.out.println(loginRequest);
		
		Customer customer = customerService.loginCustomer(loginRequest.getEmailId(), loginRequest.getPassword());
		
		System.out.println("response sent!!!");
		return ResponseEntity.ok(customer);
		
	}
	
	@GetMapping(value="/{customerPic}", produces = "image/*")
	public void fetchProductImage(@PathVariable("customerPic") String customerPic, HttpServletResponse resp) {
		System.out.println("request came for fetching customer pic");
		System.out.println("Loading file: " + customerPic);
		Resource resource = storageService.load(customerPic);
		if(resource != null) {
			try(InputStream in = resource.getInputStream()) {
				ServletOutputStream out = resp.getOutputStream();
				FileCopyUtils.copy(in, out);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("response sent!");
	}
	
	@PostMapping("update")
	public ResponseEntity<?> updateCustomer(AddCustomerRequestDto addCustomerRequestDto) {
		System.out.println("recieved request for Update customer");
		System.out.println(addCustomerRequestDto);
		
		String clientId = Helper.getAlphaNumericId();
		
		Customer customer= AddCustomerRequestDto.toEntity(addCustomerRequestDto);
		
		Customer fetchCustomerFromDb = customerService.getCustomerByClientId(customer.getClientId()).get(0);
		customer.setPic(fetchCustomerFromDb.getPic());
		customer.setPassword(fetchCustomerFromDb.getPassword());
		//this will update the Customer details
		Customer updatedCustomer = customerDao.save(customer);
		
		System.out.println(updatedCustomer);
		
		System.out.println("response sent!!!");
		return ResponseEntity.ok(updatedCustomer);
		
	}
	
	@PostMapping("update/profile")
	public ResponseEntity<?> updateCustomerProfile(AddCustomerRequestDto addCustomerRequestDto) {
		System.out.println("recieved request for Update customer profile");
		
		Customer fetchCustomerFromDb = customerService.getCustomerByClientId(addCustomerRequestDto.getClientId()).get(0);
		
		Customer customer= AddCustomerRequestDto.toEntity(addCustomerRequestDto);
		
		String customerImageName = storageService.store(addCustomerRequestDto.getPic());
        customer.setPic(customerImageName);
        customer.setPassword(fetchCustomerFromDb.getPassword());
		this.customerDao.save(customer);
		
		System.out.println("response sent!!!");
		return ResponseEntity.ok(customer);
		
	}
	
	@PostMapping("forgetPassword")
	public ResponseEntity<?> forgetPassword(@RequestBody ChangePasswordDto dto) {
		System.out.println("recieved request for customer forget password");
		
		System.out.println(dto);
		
		Customer customer = customerService.getCustomerByContact(dto.getMobileNo());
		
		if(customer != null) {
			customer.setPassword(dto.getNewPassword());
		}
		
		else {
			System.out.println("customer not found with this mobile no");
		}
		
		customerDao.save(customer);
		
		System.out.println("response sent!!!");
		return ResponseEntity.ok(customer);
		
	}
	
	@PostMapping("changePassword")
	public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto dto) {
		System.out.println("recieved request for customer change password");
		
		System.out.println(dto);
		
		Customer customer = customerDao.findById(dto.getCustomerId()).get();
		
		if(customer != null && customer.getPassword().equals(dto.getOldPassword())) {
			customer.setPassword(dto.getNewPassword());
			customerDao.save(customer);
		}
		
		else {
			System.out.println("Old password is wrong");
		}
		
		System.out.println("response sent!!!");
		return ResponseEntity.ok(customer);
		
	}

	@GetMapping("allcustomer")
	public ResponseEntity<?> getAllCustomer() throws Exception {

		System.out.println("request came for getting all customer");

		List<Customer> customers = new ArrayList<Customer>();

		customers = customerService.getAllCustomer();

		System.out.println("response sent!!!");

		return ResponseEntity.ok(customers);

	}

	@GetMapping("search/customer/clientId")
	public ResponseEntity getCustomerByClientId(@RequestParam("clientId") String clientId) throws JsonProcessingException {

		System.out.println("request came for fetching cutomer by Client id " + clientId);

		List<Customer> customers = customerService.getCustomerByClientId(clientId);


		System.out.println("response sent !!!");

		return new ResponseEntity(customers, HttpStatus.OK);

	}

	@GetMapping("search/customer/name")
	public ResponseEntity getOrdersByOrderId(@RequestParam("customerName") String customerName) throws JsonProcessingException {

		System.out.println("request came for FETCH Customer BY Customer : " + customerName);

		List<Customer> customers = customerService.getCustomerByName(customerName);

		System.out.println("response sent !!!");

		return new ResponseEntity(customers, HttpStatus.OK);

	}

}

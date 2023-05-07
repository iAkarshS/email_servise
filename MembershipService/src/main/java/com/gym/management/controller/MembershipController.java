package com.gym.management.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.gym.management.dto.AddMembershipDto;
import com.gym.management.dto.Customer;
import com.gym.management.dto.CustomerMembershipDetailResponse;
import com.gym.management.dto.Package;
import com.gym.management.entity.Membership;
import com.gym.management.service.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/membership/")
@CrossOrigin(origins = "http://localhost:3000")
public class MembershipController {
	
	@Autowired
	private MembershipService membershipService;

	
	@PostMapping("add")
	public ResponseEntity<?> addMembership( AddMembershipDto addMembershipDto) throws JsonProcessingException {
		System.out.println("recieved request for Add Customer membership");
		System.out.println(addMembershipDto);
		
		// dd-mm-yyyy
		String startDate = null;
	    String endDate= null;  
	    
	    LocalDate today = LocalDate.now();
	    startDate = today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

		RestTemplate restTemplate = new RestTemplate();
		String customersList = restTemplate.getForObject("http://localhost:8092/api/customer/search/customer/clientId?clientId={clientId}", String.class, addMembershipDto.getClientId());
		ObjectMapper mapper = new ObjectMapper();
		CollectionType listType =
				mapper.getTypeFactory().constructCollectionType(ArrayList.class, Customer.class);
		List<Customer> customers = mapper.readValue(customersList, listType);
		Customer customer = customers.get(0);

		Package packageDetail = restTemplate.getForObject("http://localhost:8094/api/package//search/package/packageId?packageId={packageId}", Package.class, addMembershipDto.getPackageId());


		if(packageDetail.getName().equals("Month")) {   
			LocalDate monthDate = today.plusMonths(1);
			endDate = monthDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		}
		
		else if(packageDetail.getName().equals("Quater")) {  
			LocalDate monthDate = today.plusMonths(3);
			endDate = monthDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		}
		
        else if(packageDetail.getName().equals("Semi-Annual")) { 
        	LocalDate monthDate = today.plusMonths(6);
			endDate = monthDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		}
		
        else if(packageDetail.getName().equals("Annual")) {
        	LocalDate monthDate = today.plusMonths(12);
			endDate = monthDate.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
		}
		
		Membership membership = new Membership();
		membership.setCustomerId(customer.getId());
		membership.setPackageId(addMembershipDto.getPackageId());
		membership.setPaymentStatus(addMembershipDto.getPaymentStatus());
		membership.setStartDate(startDate);
		membership.setEndDate(endDate);
		
		membershipService.addCustomerMembership(membership);
		
		System.out.println("response sent!!!");
		return ResponseEntity.ok(membership);
		
	}
	
	@GetMapping("all")
	public ResponseEntity<?> getAllMembership() throws Exception {
		
		System.out.println("request came for getting all membership");
		
		List<Membership> memberships = new ArrayList<>();
		
		memberships = membershipService.getAllMembership();
		
		System.out.println("response sent!!!");
		
		return ResponseEntity.ok(memberships);
		
	}
	
	@GetMapping("customer/clientId")
	public ResponseEntity<?> getCustomerMembership(@RequestParam("clientId") String clientId) throws Exception {
		
		System.out.println("request came for getting customer membership in descending order");

		RestTemplate restTemplate = new RestTemplate();
		String customersList = restTemplate.getForObject("http://localhost:8092/api/customer/search/customer/clientId?clientId={clientId}", String.class, clientId);
		ObjectMapper mapper = new ObjectMapper();
		CollectionType listType =
				mapper.getTypeFactory().constructCollectionType(ArrayList.class, Customer.class);
		List<Customer> customers = mapper.readValue(customersList, listType);
		Customer customer = customers.get(0);

		List<Membership> memberships = new ArrayList<>();
		
		memberships = membershipService.getAllCustomerMembershipById(customer.getId());
		
		List<CustomerMembershipDetailResponse> customerMembershipDetails = new ArrayList<>(); 
		
		for(Membership membership : memberships) {
			CustomerMembershipDetailResponse customerMembership = new CustomerMembershipDetailResponse();

			Package packageDetail = restTemplate.getForObject("http://localhost:8094/api/package/search/package/packageId?packageId={packageId}", Package.class, membership.getPackageId());

			customerMembership.setClientId(clientId);
			customerMembership.setCustomerName(customer.getName());
			customerMembership.setCustomerPic(customer.getPic());
			customerMembership.setEndDate(membership.getEndDate());
			customerMembership.setStartDate(membership.getStartDate());
			customerMembership.setPaymentStatus(membership.getPaymentStatus());
			customerMembership.setPrice(packageDetail.getFee());
			customerMembership.setPackageName(packageDetail.getName());
			
			customerMembershipDetails.add(customerMembership);
		}
		
		
		System.out.println(customerMembershipDetails.toString());
		System.out.println("response sent!!!");
		
		return ResponseEntity.ok(customerMembershipDetails);
		
	}

}

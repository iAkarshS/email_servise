package com.gym.management.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gym.management.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/package/")
@CrossOrigin(origins = "http://localhost:3000")
public class PackageController {
	
	@Autowired
	private PackageService packageService;
	
	@PostMapping("add")
	public ResponseEntity<?> addPackage(com.gym.management.entity.Package packge) {
		System.out.println("recieved request for Add package");
	
		System.out.println(packge);
		
		packageService.addPackage(packge);
		
		System.out.println("response sent!!!");
		return ResponseEntity.ok(packge);
		
	}
	
	@GetMapping("all")
	public ResponseEntity<?> getAllCustomer() throws Exception {
		
		System.out.println("request came for getting all package");
		
		List<com.gym.management.entity.Package> packages = new ArrayList<com.gym.management.entity.Package>();
		
		packages = packageService.getAllPackage();
		
		System.out.println("response sent!!!");
		
		return ResponseEntity.ok(packages);
		
	}

	@GetMapping("search/package/packageId")
	public ResponseEntity getCustomerByClientId(@RequestParam("packageId") Integer packageId) throws JsonProcessingException {

		com.gym.management.entity.Package packageDetail = packageService.getPackageById(packageId);

		return new ResponseEntity(packageDetail, HttpStatus.OK);

	}
	

}

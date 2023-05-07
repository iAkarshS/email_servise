package com.gym.management.service;

import java.util.List;

public interface PackageService {
	
	com.gym.management.entity.Package addPackage(com.gym.management.entity.Package packge);
    List<com.gym.management.entity.Package> getAllPackage();
    com.gym.management.entity.Package getPackageById(int packageId);
	
}

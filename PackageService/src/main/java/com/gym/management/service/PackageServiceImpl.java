package com.gym.management.service;

import com.gym.management.dao.PackageDao;
import com.gym.management.entity.Package;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageServiceImpl implements PackageService {
	
	@Autowired
	private PackageDao packageDao;

	@Override
	public com.gym.management.entity.Package addPackage(com.gym.management.entity.Package packge) {
		return packageDao.save(packge);
	}

	@Override
	public List<com.gym.management.entity.Package> getAllPackage() {
		return packageDao.findAll();
	}

	@Override
	public Package getPackageById(int packageId) {
		return packageDao.findById(packageId).get();
	}

}
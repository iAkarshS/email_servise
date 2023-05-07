package com.gym.management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gym.management.dao.AdminDao;
import com.gym.management.entity.Admin;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminDao adminDao;

	@Override
	public Admin registerAdmin(Admin admin) {
		return adminDao.save(admin);
	}

	@Override
	public Admin loginAdmin(String username, String password) {
		return adminDao.findByUsernameAndPassword(username, password);
	}

}

package com.gym.management.service;

import com.gym.management.entity.Admin;

public interface AdminService {
	
	Admin registerAdmin(Admin admin);
	Admin loginAdmin(String username, String password);

}

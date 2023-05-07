package com.gym.management.service;

import com.gym.management.entity.Membership;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MembershipService {
	
	Membership addCustomerMembership(Membership membership);
	List<Membership> getAllMembership();
	List<Membership> getAllCustomerMembershipById(int customerId);

}

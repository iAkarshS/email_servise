package com.gym.management.service;

import com.gym.management.dao.MembershipDao;
import com.gym.management.entity.Membership;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipServiceImpl implements MembershipService {

	@Autowired
	private MembershipDao membershipDao;
	
	@Override
	public Membership addCustomerMembership(Membership membership) {
		return this.membershipDao.save(membership);
	}

	@Override
	public List<Membership> getAllMembership() {
		return this.membershipDao.findAllByOrderByIdDesc();
	}

	@Override
	public List<Membership> getAllCustomerMembershipById(int customerId) {
		return this.membershipDao.findByCustomerIdOrderByIdDesc(customerId);
	}

}

package com.gym.management.dao;

import com.gym.management.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembershipDao extends JpaRepository<Membership, Integer> {
	
    List<Membership> findByCustomerIdOrderByIdDesc(int customerId);

    List<Membership> findAllByOrderByIdDesc();
}

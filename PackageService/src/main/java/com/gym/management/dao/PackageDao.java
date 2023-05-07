package com.gym.management.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageDao extends JpaRepository<com.gym.management.entity.Package, Integer>{

}

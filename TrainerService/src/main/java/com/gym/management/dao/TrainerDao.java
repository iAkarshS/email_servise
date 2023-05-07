package com.gym.management.dao;

import com.gym.management.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerDao extends JpaRepository<Trainer, Integer> {

}

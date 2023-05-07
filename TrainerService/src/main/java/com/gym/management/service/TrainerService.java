package com.gym.management.service;

import com.gym.management.entity.Trainer;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TrainerService {
	
	void registerTrainer(Trainer trainer, MultipartFile trainerPic);
	List<Trainer> getAllTrainers();
	

}
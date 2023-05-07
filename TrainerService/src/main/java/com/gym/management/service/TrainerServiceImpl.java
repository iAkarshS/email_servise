package com.gym.management.service;

import com.gym.management.dao.TrainerDao;
import com.gym.management.entity.Trainer;
import com.gym.management.utility.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Repository
public class TrainerServiceImpl implements TrainerService {

	@Autowired
	private TrainerDao trainerDao;
	
	@Autowired
	private StorageService storageService;

	@Override
	public void registerTrainer(Trainer trainer, MultipartFile trainerPic) {
        
		String productImageName = storageService.store(trainerPic);
		
		trainer.setPic(productImageName);
		
		this.trainerDao.save(trainer);
		
	}

	@Override
	public List<Trainer> getAllTrainers() {
		
		return this.trainerDao.findAll();
	}
	
	

}

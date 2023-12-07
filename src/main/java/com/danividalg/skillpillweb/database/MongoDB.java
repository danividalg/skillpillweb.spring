package com.danividalg.skillpillweb.database;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.danividalg.skillpillweb.database.model.Hello;
import com.danividalg.skillpillweb.database.repository.HelloRepository;

@Component
public class MongoDB implements DataBase {
    private HelloRepository helloRepo;
    
    public MongoDB(HelloRepository helloRepo) {
    	this.helloRepo = helloRepo;
    }
	
	public Hello getRandomHello() {
		Hello result = null;
		
		int max = (int) helloRepo.count();
		int randomNum = ThreadLocalRandom.current().nextInt(1, max + 1);
		List<Hello> helloList = helloRepo.findByNum(randomNum);
		if (!CollectionUtils.isEmpty(helloList)) {
			Hello hello = helloList.get(0);
			if (hello != null) {
				result = hello;
			}
		}
		
		return result;
	}
}

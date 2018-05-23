package com.conan.console.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheService {
	
	@Cacheable(cacheNames = "detailScore")
	public List<Float> randomList5(String accountName,float score) {
		List<Float> floatList = new ArrayList<>();
		floatList.add(10f);
		floatList.add(20f);
		floatList.add(30f);
		floatList.add(25f);
		floatList.add(15f);
		int temp = (int) (100 - score);
		while(temp>0) {
			Random rand = new Random();
	    	int index = rand.nextInt(5);
	    	if(floatList.get(index)>5) {
	    		floatList.set(index, floatList.get(index)-1);
	    		temp = temp - 1 ;
	    	}
		}
		System.out.println(score);
		return floatList;
	}
}

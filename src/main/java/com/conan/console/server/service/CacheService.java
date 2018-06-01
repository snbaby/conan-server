package com.conan.console.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.conan.console.server.utils.ConanUtils;

@Service
public class CacheService {

	@Cacheable(cacheNames = "detailScore")
	public static List<Float> randomList5(String accountName, float score) {
		List<Float> floatList = new ArrayList<>();
		
		floatList.add(3.3f);// register_info_score
		floatList.add(3.3f);// identify_info_score
		floatList.add(3.4f);// background_info_score
		
		if (score >= 80) {// 危险
			floatList.add(9.5f);// account_growup_score
			floatList.add(9.5f);// trade_frequency_score
			floatList.add(1f);// like_info_score 1危险,2可疑,3安全
		} else if (score >= 60) {// 可疑
			floatList.add(9f);// account_growup_score
			floatList.add(9f);// trade_frequency_score
			floatList.add(2f);// like_info_score 1危险,2可疑,3安全
		} else {// 安全
			floatList.add(8.5f);// account_growup_score
			floatList.add(8.5f);// trade_frequency_score
			floatList.add(3f);// like_info_score 1危险,2可疑,3安全
		}
		
		floatList.add(10f);// trade_process_score
		floatList.add(10f);// trade_habit_score
		floatList.add(10f);// logistics_character_score
		
		floatList.add(25f);// logistics_character_score
		
		floatList.add(15f);// account_history_score
		

		int temp = (int) (100 - score)*10;
		while (temp > 0) {
			Random rand = new Random();
			int index = rand.nextInt(11);
			if (floatList.get(index) > 1 && index != 5) {
				floatList.set(index, ConanUtils.fix2(floatList.get(index) - 0.1f));
				temp = temp - 1;
			}
		}
		System.out.println(score);
		return floatList;
	}
	
	
	
}

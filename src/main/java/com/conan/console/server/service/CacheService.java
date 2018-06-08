package com.conan.console.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.conan.console.server.utils.ConanUtils;

@Service
public class CacheService {

	@Cacheable(cacheNames = "detailScore")
	public JSONObject randomList5(String accountName, double score) {
		List<Double> floatList = new ArrayList<>();

		floatList.add(3.3d);// register_info_score0
		floatList.add(3.3d);// identify_info_score1
		floatList.add(3.4d);// background_info_score2

		if (score >= 80) {// 危险
			floatList.add(7d);// account_growup_score
			floatList.add(7d);// trade_frequency_score
			floatList.add(6d);// like_info_score 1危险,2可疑,3安全
		} else if (score >= 60) {// 可疑
			score = score -2;
			floatList.add(7d);// account_growup_score
			floatList.add(7d);// trade_frequency_score
			floatList.add(4d);// like_info_score 1危险,2可疑,3安全
		} else {// 安全
			score = score -4;
			floatList.add(7d);// account_growup_score
			floatList.add(7d);// trade_frequency_score
			floatList.add(2d);// like_info_score 1危险,2可疑,3安全
		}

		floatList.add(10d);// trade_process_score
		floatList.add(10d);// trade_habit_score
		floatList.add(10d);// logistics_character_score

		floatList.add(8.3d);// activity_score0
		floatList.add(8.3d);// activity_score1
		floatList.add(8.4d);// activity_score2

		floatList.add(15d);// account_history_score

		int temp = (int) (100 - score) * 10;
		while (temp > 0) {
			Random rand = new Random();
			int index = rand.nextInt(13);
			if (floatList.get(index) > 0.5 && index != 5) {
				floatList.set(index, ConanUtils.fix2(floatList.get(index) - 0.1d));
				temp = temp - 1;
			}
		}

		JSONObject resultJson = new JSONObject();
		resultJson.put("register_info_score", floatList.get(0));
		resultJson.put("identify_info_score", floatList.get(1));
		resultJson.put("background_info_score", floatList.get(2));

		resultJson.put("account_growup_score", floatList.get(3));
		resultJson.put("trade_frequency_score", floatList.get(4));
		resultJson.put("like_info_score", floatList.get(5));

		resultJson.put("trade_process_score", floatList.get(6));
		resultJson.put("trade_habit_score", floatList.get(7));
		resultJson.put("logistics_character_score", floatList.get(8));

		resultJson.put("activity_score0", floatList.get(9));
		resultJson.put("activity_score1", floatList.get(10));
		resultJson.put("activity_score2", floatList.get(11));

		resultJson.put("account_history_score", floatList.get(12));

		resultJson.put("detail_score0", ConanUtils.fix2(resultJson.getFloat("register_info_score")
				+ resultJson.getFloat("identify_info_score") + resultJson.getFloat("background_info_score")));
		
		resultJson.put("detail_score1", ConanUtils.fix2(resultJson.getFloat("account_growup_score")
				+ resultJson.getFloat("trade_frequency_score") + resultJson.getFloat("like_info_score")));
		
		resultJson.put("detail_score2", ConanUtils.fix2(resultJson.getFloat("trade_process_score")
				+ resultJson.getFloat("trade_habit_score") + resultJson.getFloat("logistics_character_score")));
		
		resultJson.put("detail_score3", ConanUtils.fix2(resultJson.getFloat("activity_score0")
				+ resultJson.getFloat("activity_score1") + resultJson.getFloat("activity_score2")));
		
		resultJson.put("detail_score4",ConanUtils.fix2( resultJson.getFloat("account_history_score")));
		return resultJson;
	}
	
	@Cacheable(cacheNames = "saftyScore")
	public JSONObject randomSaftyScoreList5(String accountName) {
		Random randScore = new Random();
		double score = randScore.nextInt(40)+20;
		
		List<Double> floatList = new ArrayList<>();

		floatList.add(3.3d);// register_info_score0
		floatList.add(3.3d);// identify_info_score1
		floatList.add(3.4d);// background_info_score2

		if (score >= 80) {// 危险
			floatList.add(7d);// account_growup_score
			floatList.add(7d);// trade_frequency_score
			floatList.add(6d);// like_info_score 1危险,2可疑,3安全
		} else if (score >= 60) {// 可疑
			score = score -2;
			floatList.add(7d);// account_growup_score
			floatList.add(7d);// trade_frequency_score
			floatList.add(4d);// like_info_score 1危险,2可疑,3安全
		} else {// 安全
			score = score -4;
			floatList.add(7d);// account_growup_score
			floatList.add(7d);// trade_frequency_score
			floatList.add(2d);// like_info_score 1危险,2可疑,3安全
		}

		floatList.add(10d);// trade_process_score
		floatList.add(10d);// trade_habit_score
		floatList.add(10d);// logistics_character_score

		floatList.add(8.3d);// activity_score0
		floatList.add(8.3d);// activity_score1
		floatList.add(8.4d);// activity_score2

		floatList.add(15d);// account_history_score

		int temp = (int) (100 - score) * 10;
		while (temp > 0) {
			Random rand = new Random();
			int index = rand.nextInt(13);
			if (floatList.get(index) > 0.5 && index != 5) {
				floatList.set(index, ConanUtils.fix2(floatList.get(index) - 0.1d));
				temp = temp - 1;
			}
		}

		JSONObject resultJson = new JSONObject();
		resultJson.put("register_info_score", floatList.get(0));
		resultJson.put("identify_info_score", floatList.get(1));
		resultJson.put("background_info_score", floatList.get(2));

		resultJson.put("account_growup_score", floatList.get(3));
		resultJson.put("trade_frequency_score", floatList.get(4));
		resultJson.put("like_info_score", floatList.get(5));

		resultJson.put("trade_process_score", floatList.get(6));
		resultJson.put("trade_habit_score", floatList.get(7));
		resultJson.put("logistics_character_score", floatList.get(8));

		resultJson.put("activity_score0", floatList.get(9));
		resultJson.put("activity_score1", floatList.get(10));
		resultJson.put("activity_score2", floatList.get(11));

		resultJson.put("account_history_score", floatList.get(12));

		resultJson.put("detail_score0", ConanUtils.fix2(resultJson.getFloat("register_info_score")
				+ resultJson.getFloat("identify_info_score") + resultJson.getFloat("background_info_score")));
		
		resultJson.put("detail_score1", ConanUtils.fix2(resultJson.getFloat("account_growup_score")
				+ resultJson.getFloat("trade_frequency_score") + resultJson.getFloat("like_info_score")));
		
		resultJson.put("detail_score2", ConanUtils.fix2(resultJson.getFloat("trade_process_score")
				+ resultJson.getFloat("trade_habit_score") + resultJson.getFloat("logistics_character_score")));
		
		resultJson.put("detail_score3", ConanUtils.fix2(resultJson.getFloat("activity_score0")
				+ resultJson.getFloat("activity_score1") + resultJson.getFloat("activity_score2")));
		
		resultJson.put("detail_score4",ConanUtils.fix2( resultJson.getFloat("account_history_score")));
		
		resultJson.put("account_score", score);
		return resultJson;
	}

}

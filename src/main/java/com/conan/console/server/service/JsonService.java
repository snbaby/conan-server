package com.conan.console.server.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;;

@Service
public class JsonService {
	@Value(value = "classpath:recharge_package.json")
	private Resource rechargePackage;

	public JSONObject getrRechargePackage() {
		InputStream stream = getClass().getClassLoader().getResourceAsStream("recharge_package.json");
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
			String s = null;
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
			br.close();
			return JSONObject.parseObject(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}

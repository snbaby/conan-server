package com.conan.console.server.service;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;;

@Service
public class JsonService {
	@Value(value = "classpath:recharge_package.json")
	private Resource rechargePackage;

	public JSONObject getrRechargePackage() {
		try {
			File rechargePackageFile = rechargePackage.getFile();
			String jsonData = this.jsonRead(rechargePackageFile);
			return JSONObject.parseObject(jsonData);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 读取文件类容为字符串
	 * 
	 * @param file
	 * @return
	 */
	private String jsonRead(File file) {
		Scanner scanner = null;
		StringBuilder buffer = new StringBuilder();
		try {
			scanner = new Scanner(file, "utf-8");
			while (scanner.hasNextLine()) {
				buffer.append(scanner.nextLine());
			}
		} catch (Exception e) {

		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
		return buffer.toString();
	}
}

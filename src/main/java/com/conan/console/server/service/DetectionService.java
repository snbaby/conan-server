package com.conan.console.server.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.conan.console.server.entity.master.CostRecord;
import com.conan.console.server.entity.master.DetectionAccount;
import com.conan.console.server.entity.master.UserBill;
import com.conan.console.server.entity.master.UserRemain;
import com.conan.console.server.entity.slave.FinalResult;
import com.conan.console.server.exception.ConanException;
import com.conan.console.server.mapper.master.CostRecordMapper;
import com.conan.console.server.mapper.master.DetectionAccountMapper;
import com.conan.console.server.mapper.master.UserBillMapper;
import com.conan.console.server.mapper.master.UserRemainMapper;
import com.conan.console.server.mapper.slave.FinalResultMapper;
import com.conan.console.server.parameter.UserGetScanHistoryParameters;
import com.conan.console.server.utils.ConanApplicationConstants;
import com.conan.console.server.utils.ConanExceptionConstants;
import com.conan.console.server.utils.ConanUtils;

@Service
public class DetectionService {

	@Autowired
	private DetectionAccountMapper detectionAccountMapper;

	@Autowired
	private CostRecordMapper costRecordMapper;

	@Autowired
	private FinalResultMapper finalResultMapper;

	@Autowired
	private UserRemainMapper userRemainMapper;
	
	@Autowired
	private UserBillMapper userBillMapper;

	@Autowired
	private MinioService minioService;

	@Transactional
	public List<DetectionAccount> getDetectionAccountPages(UserGetScanHistoryParameters userGetScanHistoryParameters,
			String user_info_id) {
		return detectionAccountMapper.selectByUserGetScanHistoryParameters(userGetScanHistoryParameters, user_info_id,
				ConanApplicationConstants.INIT_PAGE_SIZE);
	}

	@Transactional
	public JSONObject getRecentScanStat(String user_info_id) {
		JSONObject jsonObject = new JSONObject();
		CostRecord costRecord = costRecordMapper.selectLastRecordByUserInfoId(user_info_id);
		if (costRecord == null) {
			return jsonObject;
		}
		List<DetectionAccount> detectionAccountList = detectionAccountMapper.selectByRecordId(costRecord.getId());
		if (detectionAccountList == null || detectionAccountList.isEmpty()) {
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}
		int dangerScanNo = 0;// 危险账号个数
		for (DetectionAccount detectionAccount : detectionAccountList) {
			if (detectionAccount.getAccount_score() >= 80 && detectionAccount.getAccount_score() < 100) {// 80--100危险账号
				dangerScanNo++;
			}
		}
		jsonObject.put("totalScanNo", detectionAccountList.size());
		jsonObject.put("dangerScanNo", dangerScanNo);
		jsonObject.put("dangerPercent", dangerScanNo / detectionAccountList.size());
		jsonObject.put("recentScanTime", costRecord.getCreated_at());
		return jsonObject;
	}

	@Transactional
	public JSONObject scanSingnalAccount(int scan_type, String scan_account, String user_info_id) {
		UserRemain userRemain = userRemainMapper.selectByPrimaryKey(user_info_id);
		if (userRemain == null) {
			throw new ConanException(ConanExceptionConstants.USER_NOT_EXISTS_EXCEPTION_CODE,
					ConanExceptionConstants.USER_NOT_EXISTS_EXCEPTION_MESSAGE,
					ConanExceptionConstants.USER_NOT_EXISTS_EXCEPTION_HTTP_STATUS);
		}
		float gold_amount = userRemain.getGold_amount();
		float gold_coupon = userRemain.getGold_coupon();
		JSONObject resultJsonObject = new JSONObject();
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		String md5 = null;
		if (scan_account.length() == 1) {
			md5 = scan_account;
		} else {
			md5 = ConanUtils.MD5(scan_account.charAt(0) + "***" + scan_account.charAt(scan_account.length() - 1));
		}
		
		FinalResult finalResult = finalResultMapper.selectByPrimaryKey(md5);
		
		String uuid = UUID.randomUUID().toString();// 生成唯一主键
		Date date = new Date();
		UserBill userBill = new UserBill();
		userBill.setId(uuid);
		userBill.setCreated_at(date);
		userBill.setBill_type("2");
		userBill.setUpdated_at(date);
		userBill.setUser_info_id(user_info_id);
		
		CostRecord costRecord = new CostRecord();
		costRecord.setId(uuid);
		costRecord.setCreated_at(date);
		costRecord.setUpdated_at(date);
		costRecord.setCost_type("2");
		costRecord.setUser_info_id(user_info_id);
		costRecord.setUser_bill_id(uuid);
		
		DetectionAccount detectionAccount = new DetectionAccount(); 
		detectionAccount.setId(uuid);
		detectionAccount.setCreated_at(date);
		detectionAccount.setUpdated_at(date);
		detectionAccount.setCost_record_id(uuid);
		detectionAccount.setAccount_name(scan_account);
		detectionAccount.setUser_info_id(user_info_id);
		
		jsonObject.put("scan_account_id", uuid);
		jsonObject.put("created_at", date);
		jsonObject.put("account_name", scan_account);
		if(gold_coupon>=0||gold_amount>=0) {
			if(finalResult == null) {
				costRecord.setCost_gold(0f);
				
				detectionAccount.setAccount_score(0f);
				detectionAccount.setDetail_score0(0f);
				detectionAccount.setDetail_score1(0f);
				detectionAccount.setDetail_score2(0f);
				detectionAccount.setDetail_score3(0f);
				detectionAccount.setDetail_score4(0f);
				detectionAccount.setCost(0f);
				
				jsonObject.put("account_score", -2.0);
				jsonObject.put("detail_score0", 0);
				jsonObject.put("detail_score1", 0);
				jsonObject.put("detail_score2", 0);
				jsonObject.put("detail_score3", 0);
				jsonObject.put("detail_score4", 0);
				jsonObject.put("scan_cost", 0);
			}else {
				if (finalResult.getResult() < 60) {
					jsonObject.put("account_score", -1.0f);
					
					detectionAccount.setAccount_score(-1.0f);
				} else {
					jsonObject.put("account_score", finalResult.getResult().floatValue());
					
					detectionAccount.setAccount_score(finalResult.getResult().floatValue());
				}
				if(gold_coupon>0) {
					gold_coupon = gold_coupon - 1;
				}else {
					gold_amount = gold_amount -1;
				}
				costRecord.setCost_gold(1f);
				
				detectionAccount.setDetail_score0(0f);
				detectionAccount.setDetail_score1(0f);
				detectionAccount.setDetail_score2(0f);
				detectionAccount.setDetail_score3(0f);
				detectionAccount.setDetail_score4(0f);
				detectionAccount.setCost(1.0f);
				
				jsonObject.put("detail_score0", 0);
				jsonObject.put("detail_score1", 1);
				jsonObject.put("detail_score2", 2);
				jsonObject.put("detail_score3", 3);
				jsonObject.put("detail_score4", 4);
				jsonObject.put("scan_cost", 1.0);
			}
		}else {
			costRecord.setCost_gold(0f);
			
			detectionAccount.setAccount_score(-2.0f);
			detectionAccount.setDetail_score0(0f);
			detectionAccount.setDetail_score1(0f);
			detectionAccount.setDetail_score2(0f);
			detectionAccount.setDetail_score3(0f);
			detectionAccount.setDetail_score4(0f);
			detectionAccount.setCost(0f);
			
			jsonObject.put("account_score", "账号余额不足");
			jsonObject.put("detail_score0", "账号余额不足");
			jsonObject.put("detail_score1", "账号余额不足");
			jsonObject.put("detail_score2", "账号余额不足");
			jsonObject.put("detail_score3", "账号余额不足");
			jsonObject.put("detail_score4", "账号余额不足");
			jsonObject.put("scan_cost", 0);
		}
		
		jsonArray.add(jsonObject);
		
		
		userRemain.setGold_amount(gold_amount);
		userRemain.setGold_coupon(gold_coupon);
		userRemain.setUpdated_at(date);
		userRemainMapper.updateByPrimaryKey(userRemain);
		
		userBill.setRemain_gold(gold_amount+gold_coupon);
		userBillMapper.insertSelective(userBill);
			
		
		costRecord.setRemain_gold(gold_amount+gold_coupon);
		costRecordMapper.insertSelective(costRecord);
		
		detectionAccountMapper.insert(detectionAccount);
		
		resultJsonObject.put("bill_id",uuid);
		resultJsonObject.put("created_at", date);
		resultJsonObject.put("bill_type", 2);
		resultJsonObject.put("bill_amount", 1);
		resultJsonObject.put("bill_status", 2);
		resultJsonObject.put("bill_remain", gold_amount+gold_coupon);
		resultJsonObject.put("bill_digest", "单账号号检测|检测记录ID: "+uuid);
		resultJsonObject.put("cost_type", 1);
		resultJsonObject.put("scan_accounts", jsonArray);
		
		return resultJsonObject;
	}

	@Transactional
	public JSONObject scanMultiAccount(int scan_type, String scan_file, String user_info_id) {
		UserRemain userRemain = userRemainMapper.selectByPrimaryKey(user_info_id);
		if (userRemain == null) {
			throw new ConanException(ConanExceptionConstants.USER_NOT_EXISTS_EXCEPTION_CODE,
					ConanExceptionConstants.USER_NOT_EXISTS_EXCEPTION_MESSAGE,
					ConanExceptionConstants.USER_NOT_EXISTS_EXCEPTION_HTTP_STATUS);
		}
		String uuid = UUID.randomUUID().toString();// 生成唯一主键
		Date date = new Date();
		UserBill userBill = new UserBill();
		userBill.setId(uuid);
		userBill.setCreated_at(date);
		userBill.setBill_type("2");
		userBill.setUpdated_at(date);
		userBill.setUser_info_id(user_info_id);
		
		CostRecord costRecord = new CostRecord();
		costRecord.setId(uuid);
		costRecord.setCreated_at(date);
		costRecord.setUpdated_at(date);
		costRecord.setCost_type("2");
		costRecord.setUser_info_id(user_info_id);
		costRecord.setUser_bill_id(uuid);
		
		List<DetectionAccount> dectionAccountList = new ArrayList<>();
		
		JSONObject resultJsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		float gold_amount = userRemain.getGold_amount();
		float gold_coupon = userRemain.getGold_coupon();
		float bill_amount = 0;
		String export_link = null;
		List<String> scanAccountList = new ArrayList<>();
		XSSFWorkbook xwb = null;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			xwb = new XSSFWorkbook(minioService.downloadFile(scan_file));
			XSSFSheet xssfSheet = xwb.getSheetAt(0);
			for (int i = 0; i < xssfSheet.getLastRowNum(); i++) {
				String scanAccountStr = null;
				scanAccountStr = ConanUtils.getCellValueByCell(xssfSheet.getRow(i).getCell(0));
				if (StringUtils.isBlank(scanAccountStr)) {
					continue;
				} else {
					String tempString = scanAccountStr.trim();
					String md5 = null;
					if (tempString.length() == 1) {
						md5 = tempString;
						scanAccountList.add(tempString);
					} else {
						md5 = ConanUtils.MD5(tempString.charAt(0) + "***" + tempString.charAt(tempString.length() - 1));
						scanAccountList.add(md5);
					}
					Cell cell1 = xssfSheet.getRow(i).createCell(1);
					cell1.setCellType(CellType.STRING);
					cell1.setCellValue(md5);
				}
			}

			Map<String, Short> finalResultMap = new HashMap<>();
			List<FinalResult> finalResultList = finalResultMapper.selectByPrimaryKeyList(scanAccountList);
			for (FinalResult finalResult : finalResultList) {
				finalResultMap.put(finalResult.getNick_hash(), finalResult.getResult());
			}

			for (int i = 0; i < xssfSheet.getLastRowNum(); i++) {
				String dectionAccountId = UUID.randomUUID().toString();// 生成唯一主键
				Cell cell0 = xssfSheet.getRow(i).getCell(0);
				Cell cell1 = xssfSheet.getRow(i).getCell(1);
				String md5 = cell1.getStringCellValue();// 字符
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("scan_account_id", dectionAccountId);
				jsonObject.put("created_at", date);
				jsonObject.put("account_name", ConanUtils.getCellValueByCell(cell0));
				
				DetectionAccount detectionAccount = new DetectionAccount(); 
				detectionAccount.setId(dectionAccountId);
				detectionAccount.setCreated_at(date);
				detectionAccount.setUpdated_at(date);
				detectionAccount.setCost_record_id(uuid);
				detectionAccount.setAccount_name(jsonObject.getString("account_name"));
				detectionAccount.setUser_info_id(user_info_id);
				if(gold_amount<=0&&gold_coupon<=0) {
					cell1.setCellValue("账号余额不足");
					jsonObject.put("account_score", "账号余额不足");
					jsonObject.put("detail_score0", "账号余额不足");
					jsonObject.put("detail_score1", "账号余额不足");
					jsonObject.put("detail_score2", "账号余额不足");
					jsonObject.put("detail_score3", "账号余额不足");
					jsonObject.put("detail_score4", "账号余额不足");
					jsonObject.put("scan_cost", 0);
					
					detectionAccount.setAccount_score(0f);
					detectionAccount.setDetail_score0(0f);
					detectionAccount.setDetail_score1(0f);
					detectionAccount.setDetail_score2(0f);
					detectionAccount.setDetail_score3(0f);
					detectionAccount.setDetail_score4(0f);
					detectionAccount.setCost(0f);
					
				}else {
					if (finalResultMap.containsKey(md5)) {
						Short tempShort = finalResultMap.get(md5);
						if (tempShort < 60) {
							cell1.setCellValue(-1.0);
							jsonObject.put("account_score", -1.0);
							detectionAccount.setAccount_score(-1.0f);
						} else {
							cell1.setCellValue(tempShort);
							jsonObject.put("account_score", tempShort);
							detectionAccount.setAccount_score(tempShort.floatValue());
						}
						if(gold_coupon>0) {
							gold_coupon = gold_coupon - 1;
						}else {
							gold_amount = gold_amount -1;
						}
						bill_amount++;
						
						jsonObject.put("detail_score0", 0);
						jsonObject.put("detail_score1", 1);
						jsonObject.put("detail_score2", 2);
						jsonObject.put("detail_score3", 3);
						jsonObject.put("detail_score4", 4);
						jsonObject.put("scan_cost", 1.0);
						
						detectionAccount.setDetail_score0(0f);
						detectionAccount.setDetail_score1(0f);
						detectionAccount.setDetail_score2(0f);
						detectionAccount.setDetail_score3(0f);
						detectionAccount.setDetail_score4(0f);
						detectionAccount.setCost(1.0f);
						
					} else {
						cell1.setCellValue(-2.0);
						jsonObject.put("account_score", -2.0);
						jsonObject.put("detail_score0", 0);
						jsonObject.put("detail_score1", 0);
						jsonObject.put("detail_score2", 0);
						jsonObject.put("detail_score3", 0);
						jsonObject.put("detail_score4", 0);
						jsonObject.put("scan_cost", 0);
						
						detectionAccount.setAccount_score(-2.0f);
						detectionAccount.setDetail_score0(0f);
						detectionAccount.setDetail_score1(0f);
						detectionAccount.setDetail_score2(0f);
						detectionAccount.setDetail_score3(0f);
						detectionAccount.setDetail_score4(0f);
						detectionAccount.setCost(0.0f);
					}
				}
				jsonArray.add(jsonObject);
				dectionAccountList.add(detectionAccount);
			}
			
			xwb.write(os);
			byte[] content = os.toByteArray();
			minioService.uploadFile(new ByteArrayInputStream(content), scan_file, "application/octet-stream");
			export_link = minioService.presignedGetObject(scan_file);
		} catch (Exception e) {
			// TODO: handle exception
			throw new ConanException(ConanExceptionConstants.SCAN_FILE_EXCEPTION_CODE,
					ConanExceptionConstants.SCAN_FILE_EXCEPTION_MESSAGE,
					ConanExceptionConstants.SCAN_FILE_EXCEPTION_HTTP_STATUS);
		}finally {
			try {
				if(xwb!=null) {
					xwb.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		userRemain.setGold_amount(gold_amount);
		userRemain.setGold_coupon(gold_coupon);
		userRemain.setUpdated_at(date);
		userRemainMapper.updateByPrimaryKey(userRemain);
		 
		userBill.setRemain_gold(gold_amount+gold_coupon);
		userBillMapper.insertSelective(userBill);
		
		costRecord.setCost_gold(bill_amount);
		costRecord.setRemain_gold(gold_amount+gold_coupon);
		costRecordMapper.insertSelective(costRecord);
		
		for(DetectionAccount detectionAccount: dectionAccountList) {
			detectionAccountMapper.insertSelective(detectionAccount);
		}
		//detectionAccountMapper.insertList(dectionAccountList);
		
		resultJsonObject.put("bill_id",uuid);
		resultJsonObject.put("created_at", date);
		resultJsonObject.put("bill_type", 2);
		resultJsonObject.put("bill_amount", bill_amount);
		resultJsonObject.put("bill_remain", gold_amount+gold_coupon);
		resultJsonObject.put("bill_digest", "批量号检测|检测记录ID: "+uuid);
		resultJsonObject.put("cost_type", 2);
		resultJsonObject.put("bill_status", 2);
		resultJsonObject.put("scan_accounts", jsonArray);
		resultJsonObject.put("export_link", export_link);
		return resultJsonObject;
	}

	@Transactional
	public JSONObject getTopDangers(int topNum, int lastDays, String user_info_id) {
		JSONObject resultJsonObject = new JSONObject();
		List<DetectionAccount> detectionAccountList = detectionAccountMapper.selectTopDangersByUserInfoId(user_info_id,
				topNum, ConanUtils.getLastDay(lastDays));
		if (detectionAccountList == null || detectionAccountList.isEmpty()) {
			resultJsonObject.put("topDangers", new JSONArray());
			return resultJsonObject;
		}
		JSONArray jsonArray = new JSONArray();
		for (DetectionAccount detectionAccount : detectionAccountList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("account_name", detectionAccount.getAccount_name());
			jsonObject.put("account_score", detectionAccount.getAccount_score());
			jsonObject.put("detection_time", detectionAccount.getCreated_at());
			jsonArray.add(jsonObject);
		}
		resultJsonObject.put("topDangers", jsonArray);
		return resultJsonObject;
	}

}

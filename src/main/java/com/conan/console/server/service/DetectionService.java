package com.conan.console.server.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
import com.conan.console.server.entity.slave.FinalResult;
import com.conan.console.server.exception.ConanException;
import com.conan.console.server.mapper.master.CostRecordMapper;
import com.conan.console.server.mapper.master.DetectionAccountMapper;
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
	public JSONObject scan(int scan_type, String scan_account, String user_info_id) {
		JSONObject jsonObject = new JSONObject();
		FinalResult finalResult = finalResultMapper.selectByPrimaryKey(scan_account);
		jsonObject.put("result", finalResult);
		return jsonObject;
	}

	@Transactional
	public JSONObject scan(int scan_type, MultipartFile scan_file, String user_info_id) {
		JSONObject jsonObject = new JSONObject();
		List<String> scanAccountList = new ArrayList<>();
		XSSFWorkbook xwb = null;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			xwb = new XSSFWorkbook(scan_file.getInputStream());
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
				Cell cell2 = xssfSheet.getRow(i).createCell(2);
				cell2.setCellType(CellType.STRING);
				String md5 = xssfSheet.getRow(i).getCell(1).getStringCellValue();// 字符
				if (finalResultMap.containsKey(md5)) {
					Short tempShort = finalResultMap.get(md5);
					if (tempShort < 60) {
						cell2.setCellValue(-2.0);
					} else {
						cell2.setCellValue(tempShort);
					}
				} else {
					cell2.setCellValue(-1.0);
				}
			}
			
			xwb.write(os);
			byte[] content = os.toByteArray();
			System.out.println(minioService.uploadFile(new ByteArrayInputStream(content), ".xlsx", "application/octet-stream"));
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
		
		String uuid = UUID.randomUUID().toString();// 生成唯一主键
		return jsonObject;
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

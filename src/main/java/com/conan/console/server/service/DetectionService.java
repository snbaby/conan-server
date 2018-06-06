package com.conan.console.server.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.util.UriEncoder;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.conan.console.server.entity.PageInfo;
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
import com.conan.console.server.utils.ConanHttpClientUtils;
import com.conan.console.server.utils.ConanUtils;

@Service
public class DetectionService {
	private static HashMap<String, String> lockMap = new HashMap<>();
	
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

	@Autowired
	private CacheService cacheService;

	@Value("${conan.server.account-validate-url}")
	private String accountValidateUrl;

	@Transactional
	public JSONObject getDetectionAccountLink(UserGetScanHistoryParameters userGetScanHistoryParameters,
			String user_info_id) {
		JSONObject resultJsonObject = new JSONObject();

		List<DetectionAccount> detectionAccountAllResult = detectionAccountMapper
				.selectByUserGetScanHistoryAllParameters(userGetScanHistoryParameters, user_info_id);

		SXSSFWorkbook xssfWorkbook = new SXSSFWorkbook();
		SXSSFSheet xssfSheet = xssfWorkbook.createSheet("conan");
		SXSSFRow XSSFRow0 = xssfSheet.createRow(0);
		SXSSFCell XSSFCell0_0 = XSSFRow0.createCell(0);
		XSSFCell0_0.setCellValue("账号名称");
		SXSSFCell XSSFCell0_1 = XSSFRow0.createCell(1);
		XSSFCell0_1.setCellValue("账号状态");
		SXSSFCell XSSFCell0_2 = XSSFRow0.createCell(2);
		XSSFCell0_2.setCellValue("分数");
		SXSSFCell XSSFCell0_3 = XSSFRow0.createCell(3);
		XSSFCell0_3.setCellValue("账号基本分数");
		SXSSFCell XSSFCell0_4 = XSSFRow0.createCell(4);
		XSSFCell0_4.setCellValue("账号标签属性");
		SXSSFCell XSSFCell0_5 = XSSFRow0.createCell(5);
		XSSFCell0_5.setCellValue("最近行为轨迹");
		SXSSFCell XSSFCell0_6 = XSSFRow0.createCell(6);
		XSSFCell0_6.setCellValue("交易活跃度");
		SXSSFCell XSSFCell0_7 = XSSFRow0.createCell(7);
		XSSFCell0_7.setCellValue("账号历史");
		SXSSFCell XSSFCell0_8 = XSSFRow0.createCell(8);
		XSSFCell0_8.setCellValue("检测日期");

		CreationHelper createHelper = xssfWorkbook.getCreationHelper();
		CellStyle cellDateStyle = xssfWorkbook.createCellStyle();
		cellDateStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy/mm/dd hh:mm:ss"));

		for (int i = 1; i <= detectionAccountAllResult.size(); i++) {
			DetectionAccount detectionAccount = detectionAccountAllResult.get(i - 1);
			SXSSFRow SXSSFRow = xssfSheet.createRow(i);
			SXSSFCell cell0 = SXSSFRow.createCell(0);
			SXSSFCell cell1 = SXSSFRow.createCell(1);
			SXSSFCell cell2 = SXSSFRow.createCell(2);
			SXSSFCell cell3 = SXSSFRow.createCell(3);
			SXSSFCell cell4 = SXSSFRow.createCell(4);
			SXSSFCell cell5 = SXSSFRow.createCell(5);
			SXSSFCell cell6 = SXSSFRow.createCell(6);
			SXSSFCell cell7 = SXSSFRow.createCell(7);
			SXSSFCell cell8 = SXSSFRow.createCell(8);
			cell8.setCellStyle(cellDateStyle);

			cell0.setCellValue(detectionAccount.getAccount_name());
			Float score = detectionAccount.getAccount_score();
			String scoreMessage = "";
			if (score >= 80) {
				scoreMessage = ConanApplicationConstants.DANGER;
			} else if (score < 80 && score >= 60) {
				scoreMessage = ConanApplicationConstants.SUSPICIOUS;
			} else if (score == 0f) {
				scoreMessage = ConanApplicationConstants.NOT_MATCH_MESSAGE;
			} else {
				scoreMessage = ConanApplicationConstants.NOT_EXIST_MESSAGE;
			}
			cell1.setCellValue(scoreMessage);
			cell2.setCellValue(score);
			cell3.setCellValue(detectionAccount.getDetail_score0());
			cell4.setCellValue(detectionAccount.getDetail_score1());
			cell5.setCellValue(detectionAccount.getDetail_score2());
			cell6.setCellValue(detectionAccount.getDetail_score3());
			cell7.setCellValue(detectionAccount.getDetail_score4());
			cell8.setCellValue(detectionAccount.getCreated_at());
		}
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		InputStream is = null;
		try {
			xssfWorkbook.write(os);
			byte[] content = os.toByteArray();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String ymd = sdf.format(new Date());
			String objectName = user_info_id + "/" + ymd + "/" + UUID.randomUUID().toString() + ".xlsx";
			is = new ByteArrayInputStream(content);
			minioService.uploadFile(is, objectName, "application/octet-stream");
			resultJsonObject.put("export_link", minioService.presignedGetObject(objectName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (xssfWorkbook != null) {
					xssfWorkbook.close();
					xssfWorkbook.dispose();
				}
				if (os != null) {
					os.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resultJsonObject;
	}

	@Transactional
	public JSONObject getDetectionAccountPages(UserGetScanHistoryParameters userGetScanHistoryParameters,
			String user_info_id) {
		JSONObject resultJsonObject = new JSONObject();
		JSONArray recordsJsonArray = new JSONArray();
		List<DetectionAccount> detectionAccountResult = detectionAccountMapper.selectByUserGetScanHistoryParameters(
				userGetScanHistoryParameters, user_info_id, ConanApplicationConstants.INIT_PAGE_SIZE);
		int total = detectionAccountMapper.selectByUserGetScanHistoryTotalParameters(userGetScanHistoryParameters,
				user_info_id);
		PageInfo pageInfo = new PageInfo();
		pageInfo.setPageNo(userGetScanHistoryParameters.getPageNo());
		pageInfo.setPageSize(ConanApplicationConstants.INIT_PAGE_SIZE);
		pageInfo.setTotal(total);

		resultJsonObject.put("page_info", pageInfo);

		for (DetectionAccount detectionAccount : detectionAccountResult) {
			JSONObject tempObject = new JSONObject();
			tempObject.put("detection_account_id", detectionAccount.getId());
			tempObject.put("created_at", detectionAccount.getCreated_at());
			tempObject.put("account_name", detectionAccount.getAccount_name());
			tempObject.put("account_score", detectionAccount.getAccount_score());
			tempObject.put("scan_account_id", detectionAccount.getId());
			tempObject.put("account_name", detectionAccount.getAccount_name());
			tempObject.put("account_score", detectionAccount.getAccount_score());

			tempObject.put("detail_score0", detectionAccount.getDetail_score0());
			tempObject.put("register_info_score", detectionAccount.getRegister_info_score());
			tempObject.put("identify_info_score", detectionAccount.getIdentify_info_score());
			tempObject.put("background_info_score", detectionAccount.getBackground_info_score());

			tempObject.put("detail_score1", detectionAccount.getDetail_score1());
			tempObject.put("account_growup_score", detectionAccount.getAccount_growup_score());
			tempObject.put("trade_frequency_score", detectionAccount.getTrade_frequency_score());
			tempObject.put("like_info_score", detectionAccount.getLike_info_score());

			tempObject.put("detail_score2", detectionAccount.getDetail_score2());
			tempObject.put("trade_process_score", detectionAccount.getTrade_process_score());
			tempObject.put("trade_habit_score", detectionAccount.getTrade_habit_score());
			tempObject.put("logistics_character_score", detectionAccount.getLogistics_character_score());

			tempObject.put("detail_score3", detectionAccount.getDetail_score3());
			tempObject.put("activity_score0", detectionAccount.getActivity_score0());
			tempObject.put("activity_score1", detectionAccount.getActivity_score1());
			tempObject.put("activity_score2", detectionAccount.getActivity_score2());

			tempObject.put("detail_score4", detectionAccount.getDetail_score4());
			tempObject.put("account_history_score", detectionAccount.getAccount_history_score());
			recordsJsonArray.add(tempObject);
		}
		resultJsonObject.put("records", recordsJsonArray);
		return resultJsonObject;

	}

	@Transactional
	public JSONObject getRecentScanStat(String user_info_id) {
		JSONObject jsonObject = new JSONObject();
		CostRecord costRecord = costRecordMapper.selectLastRecordByUserInfoId(user_info_id);
		if (costRecord == null) {
			jsonObject.put("totalScanNo", 0);
			jsonObject.put("dangerScanNo", 0);
			jsonObject.put("dangerPercent", 0);
			jsonObject.put("recentScanTime", null);
			return jsonObject;
		}

		int totalScanNo = detectionAccountMapper.selectByUserInfoIdTotal(user_info_id);// 总检测数量
		int dangerScanNo = detectionAccountMapper.selectDangerByUserInfoIdTotal(user_info_id);// 总检测数量

		jsonObject.put("totalScanNo", totalScanNo);
		jsonObject.put("dangerScanNo", dangerScanNo);
		jsonObject.put("dangerPercent", dangerScanNo * 100f / totalScanNo);
		jsonObject.put("recentScanTime", costRecord.getCreated_at());
		return jsonObject;
	}

	@Transactional
	public JSONObject scanSingnalAccount(int scan_type, String scan_account, String user_info_id) throws InterruptedException {
		while(lockMap.containsKey(user_info_id)) {
			Thread.sleep(1000);
		}
		lockMap.put(user_info_id, user_info_id);
		
		try {
			UserRemain userRemain = userRemainMapper.selectByPrimaryKey(user_info_id);
			if (userRemain == null) {
				throw new ConanException(ConanExceptionConstants.USER_NOT_EXISTS_EXCEPTION_CODE,
						ConanExceptionConstants.USER_NOT_EXISTS_EXCEPTION_MESSAGE,
						ConanExceptionConstants.USER_NOT_EXISTS_EXCEPTION_HTTP_STATUS);
			}
			// 生产用户账单
			String uuid = UUID.randomUUID().toString();// 生成唯一主键

			float gold_amount = userRemain.getGold_amount();
			float gold_coupon = userRemain.getGold_coupon();
			float bill_amount = 0;
			String md5 = null;

			if (scan_account.length() == 1) {
				md5 = scan_account;
			} else {
				md5 = ConanUtils.MD5(scan_account.charAt(0) + "***" + scan_account.charAt(scan_account.length() - 1));
			}

			FinalResult finalResult = finalResultMapper.selectByPrimaryKey(md5);

			Boolean accountExist = ConanHttpClientUtils.httpGet(UriEncoder.encode(accountValidateUrl + scan_account));

			String dectionAccountId = UUID.randomUUID().toString();// 生成唯一主键
			DetectionAccount detectionAccount = new DetectionAccount();
			detectionAccount.setId(dectionAccountId);
			detectionAccount.setCreated_at(new Date());
			detectionAccount.setUpdated_at(new Date());
			detectionAccount.setCost_record_id(uuid);
			detectionAccount.setAccount_name(scan_account);
			detectionAccount.setUser_info_id(user_info_id);
			if (gold_coupon >= 0 || gold_amount >= 0) {
				if (finalResult == null) {
					if(accountExist) {
						detectionAccount.setAccount_score(ConanApplicationConstants.NOT_MATCH_CODE);

						detectionAccount.setDetail_score0(0f);
						detectionAccount.setRegister_info_score(0f);
						detectionAccount.setIdentify_info_score(0f);
						detectionAccount.setBackground_info_score(0f);

						detectionAccount.setDetail_score1(0f);
						detectionAccount.setAccount_growup_score(0f);
						detectionAccount.setTrade_frequency_score(0f);
						detectionAccount.setLike_info_score(0f);

						detectionAccount.setDetail_score2(0f);
						detectionAccount.setTrade_process_score(0f);
						detectionAccount.setTrade_habit_score(0f);
						detectionAccount.setLogistics_character_score(0f);

						detectionAccount.setDetail_score3(0f);
						detectionAccount.setActivity_score0(0f);
						detectionAccount.setActivity_score1(0f);
						detectionAccount.setActivity_score2(0f);

						detectionAccount.setDetail_score4(0f);
						detectionAccount.setAccount_history_score(0f);

						detectionAccount.setCost(1.0f);
					}else {
						detectionAccount.setAccount_score(ConanApplicationConstants.NOT_EXIST_CODE);

						detectionAccount.setDetail_score0(0f);
						detectionAccount.setRegister_info_score(0f);
						detectionAccount.setIdentify_info_score(0f);
						detectionAccount.setBackground_info_score(0f);

						detectionAccount.setDetail_score1(0f);
						detectionAccount.setAccount_growup_score(0f);
						detectionAccount.setTrade_frequency_score(0f);
						detectionAccount.setLike_info_score(0f);

						detectionAccount.setDetail_score2(0f);
						detectionAccount.setTrade_process_score(0f);
						detectionAccount.setTrade_habit_score(0f);
						detectionAccount.setLogistics_character_score(0f);

						detectionAccount.setDetail_score3(0f);
						detectionAccount.setActivity_score0(0f);
						detectionAccount.setActivity_score1(0f);
						detectionAccount.setActivity_score2(0f);

						detectionAccount.setDetail_score4(0f);
						detectionAccount.setAccount_history_score(0f);

						detectionAccount.setCost(1.0f);
					}
				} else {
					if(accountExist) {
						if (finalResult.getResult() < 60) {
							detectionAccount.setAccount_score(ConanApplicationConstants.NOT_MATCH_CODE);

							detectionAccount.setDetail_score0(0f);
							detectionAccount.setRegister_info_score(0f);
							detectionAccount.setIdentify_info_score(0f);
							detectionAccount.setBackground_info_score(0f);

							detectionAccount.setDetail_score1(0f);
							detectionAccount.setAccount_growup_score(0f);
							detectionAccount.setTrade_frequency_score(0f);
							detectionAccount.setLike_info_score(0f);

							detectionAccount.setDetail_score2(0f);
							detectionAccount.setTrade_process_score(0f);
							detectionAccount.setTrade_habit_score(0f);
							detectionAccount.setLogistics_character_score(0f);

							detectionAccount.setDetail_score3(0f);
							detectionAccount.setActivity_score0(0f);
							detectionAccount.setActivity_score1(0f);
							detectionAccount.setActivity_score2(0f);

							detectionAccount.setDetail_score4(0f);
							detectionAccount.setAccount_history_score(0f);

							detectionAccount.setCost(1.0f);
						} else {
							JSONObject jsonObject = cacheService.randomList5(scan_account, finalResult.getResult());
							detectionAccount.setAccount_score(finalResult.getResult() * 1f);

							detectionAccount.setDetail_score0(jsonObject.getFloat("detail_score0"));
							detectionAccount.setRegister_info_score(jsonObject.getFloat("register_info_score"));
							detectionAccount.setIdentify_info_score(jsonObject.getFloat("identify_info_score"));
							detectionAccount.setBackground_info_score(jsonObject.getFloat("background_info_score"));

							detectionAccount.setDetail_score1(jsonObject.getFloat("detail_score1"));
							detectionAccount.setAccount_growup_score(jsonObject.getFloat("account_growup_score"));
							detectionAccount.setTrade_frequency_score(jsonObject.getFloat("trade_frequency_score"));
							detectionAccount.setLike_info_score(jsonObject.getFloat("like_info_score"));

							detectionAccount.setDetail_score2(jsonObject.getFloat("detail_score2"));
							detectionAccount.setTrade_process_score(jsonObject.getFloat("trade_process_score"));
							detectionAccount.setTrade_habit_score(jsonObject.getFloat("trade_habit_score"));
							detectionAccount.setLogistics_character_score(jsonObject.getFloat("logistics_character_score"));

							detectionAccount.setDetail_score3(jsonObject.getFloat("detail_score3"));
							detectionAccount.setActivity_score0(jsonObject.getFloat("activity_score0"));
							detectionAccount.setActivity_score1(jsonObject.getFloat("activity_score1"));
							detectionAccount.setActivity_score2(jsonObject.getFloat("activity_score2"));

							detectionAccount.setDetail_score4(jsonObject.getFloat("detail_score4"));
							detectionAccount.setAccount_history_score(jsonObject.getFloat("account_history_score"));

							detectionAccount.setCost(1.0f);
						}
					}else {
						detectionAccount.setAccount_score(ConanApplicationConstants.NOT_EXIST_CODE);

						detectionAccount.setDetail_score0(0f);
						detectionAccount.setRegister_info_score(0f);
						detectionAccount.setIdentify_info_score(0f);
						detectionAccount.setBackground_info_score(0f);

						detectionAccount.setDetail_score1(0f);
						detectionAccount.setAccount_growup_score(0f);
						detectionAccount.setTrade_frequency_score(0f);
						detectionAccount.setLike_info_score(0f);

						detectionAccount.setDetail_score2(0f);
						detectionAccount.setTrade_process_score(0f);
						detectionAccount.setTrade_habit_score(0f);
						detectionAccount.setLogistics_character_score(0f);

						detectionAccount.setDetail_score3(0f);
						detectionAccount.setActivity_score0(0f);
						detectionAccount.setActivity_score1(0f);
						detectionAccount.setActivity_score2(0f);

						detectionAccount.setDetail_score4(0f);
						detectionAccount.setAccount_history_score(0f);

						detectionAccount.setCost(1.0f);
					}
				}
				if (gold_coupon > 0) {
					gold_coupon = gold_coupon - 1;
				} else {
					gold_amount = gold_amount - 1;
				}
				bill_amount++;
			} else {
				detectionAccount.setAccount_score(ConanApplicationConstants.NO_BALANCE_CODE);

				detectionAccount.setDetail_score0(0f);
				detectionAccount.setRegister_info_score(0f);
				detectionAccount.setIdentify_info_score(0f);
				detectionAccount.setBackground_info_score(0f);

				detectionAccount.setDetail_score1(0f);
				detectionAccount.setAccount_growup_score(0f);
				detectionAccount.setTrade_frequency_score(0f);
				detectionAccount.setLike_info_score(0f);

				detectionAccount.setDetail_score2(0f);
				detectionAccount.setTrade_process_score(0f);
				detectionAccount.setTrade_habit_score(0f);
				detectionAccount.setLogistics_character_score(0f);

				detectionAccount.setDetail_score3(0f);
				detectionAccount.setActivity_score0(0f);
				detectionAccount.setActivity_score1(0f);
				detectionAccount.setActivity_score2(0f);

				detectionAccount.setDetail_score4(0f);
				detectionAccount.setAccount_history_score(0f);

				detectionAccount.setCost(0.0f);
			}

			userRemain.setGold_amount(gold_amount);
			userRemain.setGold_coupon(gold_coupon);
			userRemain.setUpdated_at(new Date());
			userRemainMapper.updateByPrimaryKey(userRemain);

			UserBill userBill = new UserBill();
			userBill.setId(uuid);
			userBill.setCreated_at(new Date());
			userBill.setBill_type("2");
			userBill.setUpdated_at(new Date());
			userBill.setUser_info_id(user_info_id);
			userBill.setBill_digest("单账号检测|检测记录ID: " + uuid);
			userBill.setRemain_gold(gold_amount + gold_coupon);
			userBillMapper.insertSelective(userBill);

			// 生产cost记录
			CostRecord costRecord = new CostRecord();
			costRecord.setId(uuid);
			costRecord.setCreated_at(new Date());
			costRecord.setUpdated_at(new Date());
			costRecord.setCost_type("1");
			costRecord.setUser_info_id(user_info_id);
			costRecord.setUser_bill_id(uuid);
			costRecord.setCost_gold(bill_amount);
			costRecord.setRemain_gold(gold_amount + gold_coupon);
			costRecordMapper.insertSelective(costRecord);

			detectionAccountMapper.insert(detectionAccount);

			JSONObject resultJsonObject = new JSONObject();
			resultJsonObject.put("bill_id", uuid);
			resultJsonObject.put("created_at", new Date());
			resultJsonObject.put("bill_type", 2);
			resultJsonObject.put("bill_amount", bill_amount);
			resultJsonObject.put("bill_remain", gold_amount + gold_coupon);
			resultJsonObject.put("bill_digest", "单账号检测|检测记录ID: " + uuid);
			resultJsonObject.put("cost_type", 1);

			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("scan_account_id", detectionAccount.getId());
			jsonObject.put("created_at", new Date());
			jsonObject.put("account_name", detectionAccount.getAccount_name());
			jsonObject.put("account_score", detectionAccount.getAccount_score());

			jsonObject.put("detail_score0", detectionAccount.getDetail_score0());
			jsonObject.put("register_info_score", detectionAccount.getRegister_info_score());
			jsonObject.put("identify_info_score", detectionAccount.getIdentify_info_score());
			jsonObject.put("background_info_score", detectionAccount.getBackground_info_score());

			jsonObject.put("detail_score1", detectionAccount.getDetail_score1());
			jsonObject.put("account_growup_score", detectionAccount.getAccount_growup_score());
			jsonObject.put("trade_frequency_score", detectionAccount.getTrade_frequency_score());
			jsonObject.put("like_info_score", detectionAccount.getLike_info_score());

			jsonObject.put("detail_score2", detectionAccount.getDetail_score2());
			jsonObject.put("trade_process_score", detectionAccount.getTrade_process_score());
			jsonObject.put("trade_habit_score", detectionAccount.getTrade_habit_score());
			jsonObject.put("logistics_character_score", detectionAccount.getLogistics_character_score());

			jsonObject.put("detail_score3", detectionAccount.getDetail_score3());
			jsonObject.put("activity_score0", detectionAccount.getActivity_score0());
			jsonObject.put("activity_score1", detectionAccount.getActivity_score1());
			jsonObject.put("activity_score2", detectionAccount.getActivity_score2());

			jsonObject.put("detail_score4", detectionAccount.getDetail_score4());
			jsonObject.put("account_history_score", detectionAccount.getAccount_history_score());

			jsonObject.put("scan_cost", detectionAccount.getCost());
			jsonArray.add(jsonObject);
			resultJsonObject.put("scan_accounts", jsonArray);
			return resultJsonObject;
		} finally {
			// TODO: handle finally clause
			lockMap.remove(user_info_id);
		}
		
	}

	@Transactional
	public JSONObject scanMultiAccount(int scan_type, String scan_file, String user_info_id) throws InterruptedException {
		while(lockMap.containsKey(user_info_id)) {
			Thread.sleep(1000);
		}
		lockMap.put(user_info_id, user_info_id);
		try {
			UserRemain userRemain = userRemainMapper.selectByPrimaryKey(user_info_id);
			if (userRemain == null) {
				throw new ConanException(ConanExceptionConstants.USER_NOT_EXISTS_EXCEPTION_CODE,
						ConanExceptionConstants.USER_NOT_EXISTS_EXCEPTION_MESSAGE,
						ConanExceptionConstants.USER_NOT_EXISTS_EXCEPTION_HTTP_STATUS);
			}
			// 生产用户账单
			String uuid = UUID.randomUUID().toString();// 生成唯一主键

			float gold_amount = userRemain.getGold_amount();
			float gold_coupon = userRemain.getGold_coupon();
			float bill_amount = 0;

			String export_link = null;
			List<DetectionAccount> dectionAccountList = new ArrayList<>();
			List<String> scanAccountList = new ArrayList<>();
			XSSFWorkbook xwb = null;
			InputStream inputStream = null;
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			try {
				inputStream = minioService.downloadFile(scan_file);
				xwb = new XSSFWorkbook(inputStream);
				XSSFSheet xssfSheet = xwb.getSheetAt(0);
				for (int i = 1; i <= xssfSheet.getLastRowNum(); i++) {// 获取待检测账号列表
					String scanAccountStr = null;
					try {
						scanAccountStr = ConanUtils.getCellValueByCell(xssfSheet.getRow(i).getCell(0));
					} catch (Exception e) {
						// TODO: handle exception
						scanAccountStr = "";
					}
					if (StringUtils.isBlank(scanAccountStr)) {
						xssfSheet.createRow(i);
						Cell celli_0 = xssfSheet.getRow(i).createCell(0);
						celli_0.setCellType(CellType.STRING);
						celli_0.setCellValue("");
					} else {
						String tempString = scanAccountStr.trim();
						System.out.println(tempString);
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
				CreationHelper createHelper = xwb.getCreationHelper();
				CellStyle cellDateStyle = xwb.createCellStyle();
				cellDateStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy/mm/dd hh:mm:ss"));
				for (int i = 1; i <= xssfSheet.getLastRowNum(); i++) {
					String dectionAccountId = UUID.randomUUID().toString();// 生成唯一主键
					Cell cell0 = xssfSheet.getRow(i).getCell(0);
					if (StringUtils.isBlank(ConanUtils.getCellValueByCell(cell0))) {
						continue;
					}
					Cell cell1 = xssfSheet.getRow(i).getCell(1);
					Cell cell2 = xssfSheet.getRow(i).createCell(2);
					Cell cell3 = xssfSheet.getRow(i).createCell(3);
					Cell cell4 = xssfSheet.getRow(i).createCell(4);
					Cell cell5 = xssfSheet.getRow(i).createCell(5);
					Cell cell6 = xssfSheet.getRow(i).createCell(6);
					Cell cell7 = xssfSheet.getRow(i).createCell(7);
					Cell cell8 = xssfSheet.getRow(i).createCell(8);
					cell8.setCellStyle(cellDateStyle);
					String md5 = ConanUtils.getCellValueByCell(cell1);// 字符

					// 账号记录
					DetectionAccount detectionAccount = new DetectionAccount();
					detectionAccount.setId(dectionAccountId);
					detectionAccount.setCreated_at(new Date());
					detectionAccount.setUpdated_at(new Date());
					detectionAccount.setCost_record_id(uuid);
					detectionAccount.setAccount_name(ConanUtils.getCellValueByCell(cell0));
					detectionAccount.setUser_info_id(user_info_id);

					if (gold_amount <= 0 && gold_coupon <= 0) {
						// 设置excel
						cell1.setCellValue(ConanApplicationConstants.NO_BALANCE_MESSAGE);
						cell2.setCellValue(ConanApplicationConstants.NO_BALANCE_CODE);
						cell3.setCellValue(0);
						cell4.setCellValue(0);
						cell5.setCellValue(0);
						cell6.setCellValue(0);
						cell7.setCellValue(0);
						cell8.setCellValue(new Date());

						detectionAccount.setDetail_score0(0f);
						detectionAccount.setRegister_info_score(0f);
						detectionAccount.setIdentify_info_score(0f);
						detectionAccount.setBackground_info_score(0f);

						detectionAccount.setDetail_score1(0f);
						detectionAccount.setAccount_growup_score(0f);
						detectionAccount.setTrade_frequency_score(0f);
						detectionAccount.setLike_info_score(0f);

						detectionAccount.setDetail_score2(0f);
						detectionAccount.setTrade_process_score(0f);
						detectionAccount.setTrade_habit_score(0f);
						detectionAccount.setLogistics_character_score(0f);

						detectionAccount.setDetail_score3(0f);
						detectionAccount.setActivity_score0(0f);
						detectionAccount.setActivity_score1(0f);
						detectionAccount.setActivity_score2(0f);

						detectionAccount.setDetail_score4(0f);
						detectionAccount.setAccount_history_score(0f);

						detectionAccount.setCost(0.0f);
					} else {
						if (finalResultMap.containsKey(md5)) {
							Short tempShort = finalResultMap.get(md5);
							if (tempShort < 60) {
								cell1.setCellValue(ConanApplicationConstants.NOT_MATCH_MESSAGE);
								cell2.setCellValue(ConanApplicationConstants.NOT_MATCH_CODE);
								cell3.setCellValue(0);
								cell4.setCellValue(0);
								cell5.setCellValue(0);
								cell6.setCellValue(0);
								cell7.setCellValue(0);
								cell8.setCellValue(new Date());

								detectionAccount.setAccount_score(ConanApplicationConstants.NOT_MATCH_CODE);

								detectionAccount.setDetail_score0(0f);
								detectionAccount.setRegister_info_score(0f);
								detectionAccount.setIdentify_info_score(0f);
								detectionAccount.setBackground_info_score(0f);

								detectionAccount.setDetail_score1(0f);
								detectionAccount.setAccount_growup_score(0f);
								detectionAccount.setTrade_frequency_score(0f);
								detectionAccount.setLike_info_score(0f);

								detectionAccount.setDetail_score2(0f);
								detectionAccount.setTrade_process_score(0f);
								detectionAccount.setTrade_habit_score(0f);
								detectionAccount.setLogistics_character_score(0f);

								detectionAccount.setDetail_score3(0f);
								detectionAccount.setActivity_score0(0f);
								detectionAccount.setActivity_score1(0f);
								detectionAccount.setActivity_score2(0f);

								detectionAccount.setDetail_score4(0f);
								detectionAccount.setAccount_history_score(0f);

								detectionAccount.setCost(1.0f);

							} else if (tempShort >= 60 && tempShort < 80) {
								JSONObject tempJsonObject = cacheService.randomList5(ConanUtils.getCellValueByCell(cell0),
										tempShort);
								cell1.setCellValue(ConanApplicationConstants.SUSPICIOUS);
								cell2.setCellValue(tempShort);
								cell3.setCellValue(tempJsonObject.getFloat("detail_score0"));
								cell4.setCellValue(tempJsonObject.getFloat("detail_score1"));
								cell5.setCellValue(tempJsonObject.getFloat("detail_score2"));
								cell6.setCellValue(tempJsonObject.getFloat("detail_score3"));
								cell7.setCellValue(tempJsonObject.getFloat("detail_score4"));
								cell8.setCellValue(new Date());

								detectionAccount.setAccount_score(tempShort.floatValue());

								detectionAccount.setDetail_score0(tempJsonObject.getFloat("detail_score0"));
								detectionAccount.setRegister_info_score(tempJsonObject.getFloat("register_info_score"));
								detectionAccount.setIdentify_info_score(tempJsonObject.getFloat("identify_info_score"));
								detectionAccount.setBackground_info_score(tempJsonObject.getFloat("background_info_score"));

								detectionAccount.setDetail_score1(tempJsonObject.getFloat("detail_score1"));
								detectionAccount.setAccount_growup_score(tempJsonObject.getFloat("account_growup_score"));
								detectionAccount.setTrade_frequency_score(tempJsonObject.getFloat("trade_frequency_score"));
								detectionAccount.setLike_info_score(tempJsonObject.getFloat("like_info_score"));

								detectionAccount.setDetail_score2(tempJsonObject.getFloat("detail_score2"));
								detectionAccount.setTrade_process_score(tempJsonObject.getFloat("trade_process_score"));
								detectionAccount.setTrade_habit_score(tempJsonObject.getFloat("trade_habit_score"));
								detectionAccount
										.setLogistics_character_score(tempJsonObject.getFloat("logistics_character_score"));

								detectionAccount.setDetail_score3(tempJsonObject.getFloat("detail_score3"));
								detectionAccount.setActivity_score0(tempJsonObject.getFloat("activity_score0"));
								detectionAccount.setActivity_score1(tempJsonObject.getFloat("activity_score1"));
								detectionAccount.setActivity_score2(tempJsonObject.getFloat("activity_score2"));

								detectionAccount.setDetail_score4(tempJsonObject.getFloat("detail_score4"));
								detectionAccount.setAccount_history_score(tempJsonObject.getFloat("account_history_score"));

								detectionAccount.setCost(1.0f);
							} else {
								JSONObject tempJsonObject = cacheService.randomList5(ConanUtils.getCellValueByCell(cell0),
										tempShort);
								cell1.setCellValue(ConanApplicationConstants.DANGER);
								cell2.setCellValue(tempShort);
								cell3.setCellValue(tempJsonObject.getFloat("detail_score0"));
								cell4.setCellValue(tempJsonObject.getFloat("detail_score1"));
								cell5.setCellValue(tempJsonObject.getFloat("detail_score2"));
								cell6.setCellValue(tempJsonObject.getFloat("detail_score3"));
								cell7.setCellValue(tempJsonObject.getFloat("detail_score4"));
								cell8.setCellValue(new Date());

								detectionAccount.setAccount_score(tempShort.floatValue());

								detectionAccount.setDetail_score0(tempJsonObject.getFloat("detail_score0"));
								detectionAccount.setRegister_info_score(tempJsonObject.getFloat("register_info_score"));
								detectionAccount.setIdentify_info_score(tempJsonObject.getFloat("identify_info_score"));
								detectionAccount.setBackground_info_score(tempJsonObject.getFloat("background_info_score"));

								detectionAccount.setDetail_score1(tempJsonObject.getFloat("detail_score1"));
								detectionAccount.setAccount_growup_score(tempJsonObject.getFloat("account_growup_score"));
								detectionAccount.setTrade_frequency_score(tempJsonObject.getFloat("trade_frequency_score"));
								detectionAccount.setLike_info_score(tempJsonObject.getFloat("like_info_score"));

								detectionAccount.setDetail_score2(tempJsonObject.getFloat("detail_score2"));
								detectionAccount.setTrade_process_score(tempJsonObject.getFloat("trade_process_score"));
								detectionAccount.setTrade_habit_score(tempJsonObject.getFloat("trade_habit_score"));
								detectionAccount
										.setLogistics_character_score(tempJsonObject.getFloat("logistics_character_score"));

								detectionAccount.setDetail_score3(tempJsonObject.getFloat("detail_score3"));
								detectionAccount.setActivity_score0(tempJsonObject.getFloat("activity_score0"));
								detectionAccount.setActivity_score1(tempJsonObject.getFloat("activity_score1"));
								detectionAccount.setActivity_score2(tempJsonObject.getFloat("activity_score2"));

								detectionAccount.setDetail_score4(tempJsonObject.getFloat("detail_score4"));
								detectionAccount.setAccount_history_score(tempJsonObject.getFloat("account_history_score"));

								detectionAccount.setCost(1.0f);
							}
						} else {
							cell1.setCellValue(ConanApplicationConstants.NOT_MATCH_MESSAGE);
							cell2.setCellValue(ConanApplicationConstants.NOT_MATCH_CODE);
							cell3.setCellValue(0);
							cell4.setCellValue(0);
							cell5.setCellValue(0);
							cell6.setCellValue(0);
							cell7.setCellValue(0);
							cell8.setCellValue(new Date());

							detectionAccount.setAccount_score(ConanApplicationConstants.NOT_MATCH_CODE);

							detectionAccount.setDetail_score0(0f);
							detectionAccount.setRegister_info_score(0f);
							detectionAccount.setIdentify_info_score(0f);
							detectionAccount.setBackground_info_score(0f);

							detectionAccount.setDetail_score1(0f);
							detectionAccount.setAccount_growup_score(0f);
							detectionAccount.setTrade_frequency_score(0f);
							detectionAccount.setLike_info_score(0f);

							detectionAccount.setDetail_score2(0f);
							detectionAccount.setTrade_process_score(0f);
							detectionAccount.setTrade_habit_score(0f);
							detectionAccount.setLogistics_character_score(0f);

							detectionAccount.setDetail_score3(0f);
							detectionAccount.setActivity_score0(0f);
							detectionAccount.setActivity_score1(0f);
							detectionAccount.setActivity_score2(0f);

							detectionAccount.setDetail_score4(0f);
							detectionAccount.setAccount_history_score(0f);

							detectionAccount.setCost(1.0f);
						}
						if (gold_coupon > 0) {
							gold_coupon = gold_coupon - 1;
						} else {
							gold_amount = gold_amount - 1;
						}
						bill_amount++;
					}
					dectionAccountList.add(detectionAccount);
				}

				xwb.write(os);
				byte[] content = os.toByteArray();
				minioService.uploadFile(new ByteArrayInputStream(content), scan_file, "application/octet-stream");
				export_link = minioService.presignedGetObject(scan_file);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new ConanException(ConanExceptionConstants.SCAN_FILE_EXCEPTION_CODE,
						ConanExceptionConstants.SCAN_FILE_EXCEPTION_MESSAGE,
						ConanExceptionConstants.SCAN_FILE_EXCEPTION_HTTP_STATUS);
			} finally {
				try {
					if (xwb != null) {
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
				try {
					if (inputStream != null) {
						inputStream.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			userRemain.setGold_amount(gold_amount);
			userRemain.setGold_coupon(gold_coupon);
			userRemain.setUpdated_at(new Date());
			userRemainMapper.updateByPrimaryKey(userRemain);

			UserBill userBill = new UserBill();
			userBill.setId(uuid);
			userBill.setCreated_at(new Date());
			userBill.setBill_type("2");
			userBill.setUpdated_at(new Date());
			userBill.setUser_info_id(user_info_id);
			userBill.setBill_digest("批量账号检测|检测记录ID: " + uuid);
			userBill.setRemain_gold(gold_amount + gold_coupon);
			userBillMapper.insertSelective(userBill);

			// 生产cost记录
			CostRecord costRecord = new CostRecord();
			costRecord.setId(uuid);
			costRecord.setCreated_at(new Date());
			costRecord.setUpdated_at(new Date());
			costRecord.setCost_type("2");
			costRecord.setUser_info_id(user_info_id);
			costRecord.setUser_bill_id(uuid);
			costRecord.setCost_gold(bill_amount);
			costRecord.setRemain_gold(gold_amount + gold_coupon);
			costRecordMapper.insertSelective(costRecord);

			JSONObject resultJsonObject = new JSONObject();
			resultJsonObject.put("bill_id", uuid);
			resultJsonObject.put("created_at", new Date());
			resultJsonObject.put("bill_type", 2);
			resultJsonObject.put("bill_amount", bill_amount);
			resultJsonObject.put("bill_remain", gold_amount + gold_coupon);
			resultJsonObject.put("bill_digest", "批量账号检测|检测记录ID: " + uuid);
			resultJsonObject.put("cost_type", 2);

			resultJsonObject.put("export_link", export_link);

			JSONArray jsonArray = new JSONArray();

			List<DetectionAccount> detectionAccountInsertList = new ArrayList<>();
			for (int i = 0; i < dectionAccountList.size(); i++) {
				DetectionAccount detectionAccount = dectionAccountList.get(i);
				/*
				 * JSONObject jsonObject = new JSONObject(); jsonObject.put("scan_account_id",
				 * detectionAccount.getId()); jsonObject.put("created_at", new Date());
				 * jsonObject.put("account_name", detectionAccount.getAccount_name());
				 * jsonObject.put("account_score", detectionAccount.getAccount_score());
				 * jsonObject.put("detail_score0",detectionAccount.getDetail_score0());
				 * jsonObject.put("detail_score1",detectionAccount.getDetail_score1());
				 * jsonObject.put("detail_score2",detectionAccount.getDetail_score2());
				 * jsonObject.put("detail_score3",detectionAccount.getDetail_score3());
				 * jsonObject.put("detail_score4",detectionAccount.getDetail_score4());
				 * jsonObject.put("scan_cost",detectionAccount.getCost());
				 * jsonArray.add(jsonObject);
				 */
				detectionAccountInsertList.add(dectionAccountList.get(i));
				if (detectionAccountInsertList.size() == 500 || i == dectionAccountList.size() - 1) {
					detectionAccountMapper.insertList(detectionAccountInsertList);
					detectionAccountInsertList.clear();
				}
			}
			resultJsonObject.put("scan_accounts", jsonArray);
			return resultJsonObject;
		}finally {
			lockMap.remove(user_info_id);
		}
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

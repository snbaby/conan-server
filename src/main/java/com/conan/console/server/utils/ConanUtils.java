package com.conan.console.server.utils;

import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;

public class ConanUtils {
	public static Date getLastDay(int lastDays) {
		Date date = new Date();// 取时间
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE, -1 * lastDays);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	public static Date getStartTime() {  
        Calendar todayStart = Calendar.getInstance();  
        todayStart.set(Calendar.HOUR_OF_DAY, 0);  
        todayStart.set(Calendar.MINUTE, 0);  
        todayStart.set(Calendar.SECOND, 0);  
        todayStart.set(Calendar.MILLISECOND, 0);  
        return todayStart.getTime();  
    } 

	public static String MD5(String s) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = md.digest(s.getBytes("utf-8"));
			return toHex(bytes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String toHex(byte[] bytes) {

		final char[] HEX_DIGITS = "0123456789abcdef".toCharArray();
		StringBuilder ret = new StringBuilder(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
			ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
		}
		return ret.toString();
	}

	public static String getCellValueByCell(Cell cell) {
		// 判断是否为null或空串
		if (cell == null || cell.toString().trim().equals("")) {
			return "";
		}
		String cellValue = "";
		int cellType = cell.getCellType();

		switch (cellType) {
		case Cell.CELL_TYPE_STRING: // 字符串类型
			cellValue = cell.getStringCellValue().trim();
			cellValue = StringUtils.isEmpty(cellValue) ? "" : cellValue;
			break;
		case Cell.CELL_TYPE_BOOLEAN: // 布尔类型
			cellValue = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_NUMERIC: // 数值类型
			if (HSSFDateUtil.isCellDateFormatted(cell)) { // 判断日期类型
				cellValue = cell.getDateCellValue().toGMTString();
			} else { // 否
				cellValue = new DecimalFormat("#.######").format(cell.getNumericCellValue());
			}
			break;
		default: // 其它类型，取空串吧
			cellValue = "";
			break;
		}
		return cellValue;
	}

	public static List<Float> randomList5(String str, float score) {
		DecimalFormat decimalFormat = new DecimalFormat(".00");
		String strMd5 = MD5(str);
		int i0 = Integer.parseInt(Integer.toHexString(strMd5.charAt(0)));
		int i1 = Integer.parseInt(Integer.toHexString(strMd5.charAt(1)));
		int i2 = Integer.parseInt(Integer.toHexString(strMd5.charAt(2)));
		int i3 = Integer.parseInt(Integer.toHexString(strMd5.charAt(3)));
		int i4 = Integer.parseInt(Integer.toHexString(strMd5.charAt(4)));
		int total = i0 + i1 + i2 + i3 + i4;
		List<Float> resultList = new ArrayList<>();
		float f0 = Float.valueOf(decimalFormat.format(score * i0 / total));
		float f1 = Float.valueOf(decimalFormat.format(score * i1 / total));
		float f2 = Float.valueOf(decimalFormat.format(score * i2 / total));
		float f3 = Float.valueOf(decimalFormat.format(score * i3 / total));
		float f4 = score - f0 - f1 - f2 - f3;
		resultList.add(f0);
		resultList.add(f1);
		resultList.add(f2);
		resultList.add(f3);
		resultList.add(f4);
		return resultList;
	}
}

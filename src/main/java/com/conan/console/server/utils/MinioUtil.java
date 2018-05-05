package com.conan.console.server.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import io.minio.MinioClient;

public class MinioUtil {
	
	private static String minio_url = "http://120.79.197.22:9000";  
	  
    private static String minio_ak = "QUMQ3067MN3Y92L22BQJ";  
  
    private static String minio_sk = "cpi/k6A5kk8hbOA+pT+LTIFs5/1p5hpNVl4Z28MJ";  
  
    private static String minio_bucketName = "conan-server-2.0";  
  
  
    /** 
     *  
     * @Title: uploadImage 
     * @Description:上传图片 
     * @param inputStream 
     * @param suffix 
     * @return 
     * @throws Exception 
     */  
    public static JSONObject uploadImage(InputStream inputStream, String suffix) throws Exception {  
        return upload(inputStream, suffix, "image/jpeg");  
    }  
  
  
    /** 
     * @Title: uploadVideo 
     * @Description:上传视频 
     * @param inputStream 
     * @param suffix 
     * @return 
     * @throws Exception 
     */  
    public static JSONObject uploadVideo(InputStream inputStream, String suffix) throws Exception {
        return upload(inputStream, suffix, "video/mp4");  
    }  
  
  
    /** 
     * @Title: uploadVideo 
     * @Description:上传文件 
     * @param inputStream 
     * @param suffix 
     * @return 
     * @throws Exception 
     */  
    public static JSONObject uploadFile(InputStream inputStream, String suffix) throws Exception {  
        return upload(inputStream, suffix, "application/octet-stream");  
    }  
  
  
    /** 
     * 上传字符串大文本内容 
     *  
     * @Title: uploadString 
     * @Description:描述方法 
     * @param str 
     * @return 
     * @throws Exception 
     */  
    public static JSONObject uploadString(String str) throws Exception {  
        if (StringUtils.isBlank(str)) {  
            return new JSONObject();  
        }  
        InputStream inputStream = new ByteArrayInputStream(str.getBytes());  
        return upload(inputStream, null, "text/html");  
    }  
  
  
    /** 
     * @Title: upload 
     * @Description:上传主功能 
     * @return 
     * @throws Exception 
     */  
    private static JSONObject upload(InputStream inputStream, String suffix, String contentType) throws Exception {  
        JSONObject jsonObject = new JSONObject();  
        MinioClient minioClient = new MinioClient(minio_url, minio_ak, minio_sk);  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
        String ymd = sdf.format(new Date());  
        String objectName = ymd + "/" + UUID.randomUUID().toString() + (suffix != null ? suffix : "");  
        minioClient.putObject(minio_bucketName, objectName, inputStream, contentType);  
        String url = minioClient.getObjectUrl(minio_bucketName, objectName);  
        jsonObject.put("url", url);  
        return jsonObject;  
    }  
}

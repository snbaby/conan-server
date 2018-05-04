/**
 * 
 */
package com.conan.console.server.service;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.minio.MinioClient;

/**
 * @author Administrator Paul Wang
 * @version 创建时间：2017年12月21日 下午4:19:06
 * @email 12900985@qq.com
 * @company Seadun
 */

@Service
public class MinioService {

	@Value("${minio.endpoint}")
	private String minio_endpoint;

	@Value("${minio.ak}")
	private String minio_accessKey;

	@Value("${minio.sk}")
	private String minio_secretKey;

	@Value("${minio.backet-name}")
	private String minio_bucketName;

	public String uploadFile(InputStream inputStream, String suffix, String contentType) throws Exception {
		MinioClient minioClient = new MinioClient(minio_endpoint, minio_accessKey, minio_secretKey );
		if(!minioClient.bucketExists(minio_bucketName)) {
			minioClient.makeBucket(minio_bucketName);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String ymd = sdf.format(new Date());
		String objectName = ymd + "/" + UUID.randomUUID().toString() + (suffix != null ? suffix : "");
		minioClient.putObject(minio_bucketName, objectName, inputStream, contentType);
		String url = minioClient.getObjectUrl(minio_bucketName, objectName);
		return url;
	}
	
	
	
}

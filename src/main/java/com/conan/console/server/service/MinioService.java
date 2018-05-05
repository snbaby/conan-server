/**
 * 
 */
package com.conan.console.server.service;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xmlpull.v1.XmlPullParserException;

import com.conan.console.server.exception.ConanException;
import com.conan.console.server.utils.ConanExceptionConstants;

import io.minio.MinioClient;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidArgumentException;
import io.minio.errors.InvalidBucketNameException;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidExpiresRangeException;
import io.minio.errors.InvalidPortException;
import io.minio.errors.NoResponseException;
import io.minio.errors.RegionConflictException;

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

	public String uploadFile(InputStream inputStream, String objectName, String contentType) {
		MinioClient minioClient;
		String url = "";
		try {
			minioClient = new MinioClient(minio_endpoint, minio_accessKey, minio_secretKey );
			if(!minioClient.bucketExists(minio_bucketName)) {
				minioClient.makeBucket(minio_bucketName);
			}
			minioClient.putObject(minio_bucketName, objectName, inputStream, contentType);
			url = minioClient.getObjectUrl(minio_bucketName, objectName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new ConanException(ConanExceptionConstants.INTERNAL_SERVER_ERROR_CODE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE,
					ConanExceptionConstants.INTERNAL_SERVER_ERROR_HTTP_STATUS);
		}
		
		return url;
	}
	
}

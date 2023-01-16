package com.news.storage.impl;

import com.news.storage.StorageService;
import io.minio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StorageServiceImpl implements StorageService {

    private static final String DEFAULT_BUCKET = "news";

    @Autowired
    private MinioClient minioClient;

    public String upload(String prefix, String filename, InputStream inputStream) {
        if (StringUtils.isEmpty(prefix)) {
            prefix = DEFAULT_BUCKET;
        }
        BucketExistsArgs bucketExistsArgs = BucketExistsArgs.builder().bucket(prefix).build();
        try {
            if (!minioClient.bucketExists(bucketExistsArgs)) {
                // 桶不存在
                MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder().bucket(prefix).build();
                minioClient.makeBucket(makeBucketArgs);
            }
            // 获取yyyy/mm/dd日期格式
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            String date = format.format(new Date());
            // 上传文件
            String path = date + "/" + filename;
            PutObjectArgs putObjectArgs = PutObjectArgs.builder().bucket(prefix).object(path).stream(inputStream, inputStream.available(), -1).build();
            minioClient.putObject(putObjectArgs);
            return path;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public InputStream download(String prefix, String path) {
        if (StringUtils.isEmpty(prefix)) {
            prefix = DEFAULT_BUCKET;
        }
        BucketExistsArgs bucketExistsArgs = BucketExistsArgs.builder().bucket(prefix).build();
        try {
            if (!minioClient.bucketExists(bucketExistsArgs)) {
                throw new RuntimeException("don't found file");
            }
            return minioClient.getObject(GetObjectArgs.builder().bucket(prefix).object(path).build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

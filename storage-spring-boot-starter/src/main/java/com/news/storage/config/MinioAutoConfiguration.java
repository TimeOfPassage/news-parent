package com.news.storage.config;

import com.news.storage.StorageService;
import com.news.storage.impl.StorageServiceImpl;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = MinioProperties.class)
@ConditionalOnClass(MinioProperties.class)
public class MinioAutoConfiguration {

    @Autowired
    private MinioProperties minioProperties;

    @Bean
    public MinioClient minioClient() {
        MinioClient minioClient = MinioClient.builder().endpoint(minioProperties.getApi()).credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey()).build();
        return minioClient;
    }

    @Bean
    public StorageService storageService() {
        return new StorageServiceImpl();
    }
}

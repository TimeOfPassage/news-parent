package com.news.article;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class NewsArticleApplication implements InitializingBean {
    public static void main(String[] args) {
        SpringApplication.run(NewsArticleApplication.class, args);
    }

    @Value("${minio.api}")
    private String api;

    public void afterPropertiesSet() throws Exception {
        System.out.println(api);
    }
}

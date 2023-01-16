package com.news.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class NewsUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(NewsUserApplication.class, args);
    }
}

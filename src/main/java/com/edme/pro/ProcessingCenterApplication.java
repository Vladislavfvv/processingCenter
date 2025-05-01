package com.edme.pro;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Slf4j
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.edme.pro.repository")
@EntityScan(basePackages = "com.edme.pro.model")
public class ProcessingCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProcessingCenterApplication.class, args);
        log.info("Application started successfully!");
    }

}

package com.ml.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import brave.sampler.Sampler;


//https://thepracticaldeveloper.com/2018/03/18/spring-boot-service-discovery-eureka/
// This application is the eureka microservice,
//that is client to Eureka Server instance
@EnableEurekaClient
@SpringBootApplication
@EnableWebMvc
@ComponentScan(basePackages = "com.ml.controller")
public class MLServerApp{

    public static void main(String[] args) {
    	
    	System.setProperty("spring.config.name", "MLService");
        SpringApplication.run(MLServerApp.class, args);
    }
    
    @Bean
    public Sampler defaultSampler() {
    	return Sampler.ALWAYS_SAMPLE;
    }
}
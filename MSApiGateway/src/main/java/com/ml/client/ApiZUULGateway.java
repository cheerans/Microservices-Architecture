package com.ml.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//import brave.sampler.Sampler;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
@EnableWebMvc
@ComponentScan(basePackages = "com.ml.client")
public class ApiZUULGateway {

    public static void main(String[] args) {
    	
    	System.setProperty("spring.config.name", "ApiZUULGateway");
        SpringApplication.run(ApiZUULGateway.class, args);        
        
        //MLMicSvcClient client = new MLMicSvcClient();
        //client.callMLService();
    }
    
    @Bean
    public Sampler defaultSampler() {
    	return Sampler.ALWAYS_SAMPLE;
    }
}

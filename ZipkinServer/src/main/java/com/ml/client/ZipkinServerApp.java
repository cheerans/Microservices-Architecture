package com.ml.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
public class ZipkinServerApp {

	// This object is the eureka server registry maintainer app
    public static void main(String[] args) {
    	
    	System.setProperty("spring.config.name", "ZipkinServer");
        SpringApplication.run(ZipkinServerApp.class, args);        
    }
}
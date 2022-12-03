package com.kruger.msvc.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsvcCourseApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcCourseApplication.class, args);
	}

}

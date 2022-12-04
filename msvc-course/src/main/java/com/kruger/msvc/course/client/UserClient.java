package com.kruger.msvc.course.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name="msvc-user")
public interface UserClient {
	
	@RequestMapping("/api/user/port")
	String avaliblePort();
}

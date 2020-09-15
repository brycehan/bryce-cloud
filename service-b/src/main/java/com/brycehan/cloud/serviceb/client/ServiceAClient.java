package com.brycehan.cloud.serviceb.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@FeignClient(name = "service-a", fallback = ServiceAClient.ServiceAClientFallback.class)
public interface ServiceAClient {

	@GetMapping
	String index();
	
	@Component
	@Slf4j
	class ServiceAClientFallback implements ServiceAClient{

		@Override
		public String index() {
			log.info("异常发生，进入fallback方法！");
			return "Service A failed! - falling back.";
		}
		
	}

}

package com.example.restController;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan({"com"})


public class RestControllerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestControllerApplication.class, args);
	}
//	@Bean
//	public HttpTraceRepository httpTraceRepository() {
//		return new InMemoryHttpTraceRepository();
//	}
@Bean
public RestTemplate restTemplate()
{
	return new RestTemplate();
}
}

package com.learning.employee.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class EmployeeAppConfiguration {
    @Bean
    ModelMapper getModelMapper() {
		return new ModelMapper();
	}
    
    @Bean
    RestTemplate restTemplate(@Value("${addressservice.base.url}") String uri, RestTemplateBuilder builder) {
     
    	return builder.rootUri(uri)
    		.build();
    }
    
    @Bean
    WebClient getwebClient(@Value("${addressservice.base.url}") String baseURL) {
    	return WebClient.builder().baseUrl(baseURL).build();
    }
}

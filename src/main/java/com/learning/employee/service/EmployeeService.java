package com.learning.employee.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.learning.employee.datamodel.AddressResponse;
import com.learning.employee.datamodel.EmployeeResponse;
import com.learning.employee.entity.EmployeeEntity;
import com.learning.employee.repository.EmployeeRepository;

import feignclient.AddressFeignClient;

@Service
public class EmployeeService {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private WebClient webClient;
	
	@Autowired
	private AddressFeignClient addressFeignClient;
	
	public ResponseEntity<EmployeeResponse> getEmployeeById(int id) {
		
		EmployeeEntity employeeEntity = employeeRepository.findEmployeeById(id);
		EmployeeResponse employeeResponse = modelMapper.map(employeeEntity, EmployeeResponse.class);
		AddressResponse addressResponse = addressFeignClient.getAddressByEmployeeId(id);//getAddressByWebClient(id);//getAddressByRestTemplate(id);
		employeeResponse.setAddress(addressResponse);
		return ResponseEntity.status(HttpStatus.OK).body(employeeResponse);
		
	}
	
	public AddressResponse getAddressByRestTemplate(int id) {
		return restTemplate.getForObject("/address/{id}", AddressResponse.class, id);
	}
	
	public AddressResponse getAddressByWebClient(int id) {
		return webClient.get().uri("/address/"+id).retrieve().bodyToMono(AddressResponse.class).block();
	}
}

package com.webproje.arackiralama.Api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.webproje.arackiralama.Business.abstracts.CustomerService;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Entity.dto.customerDtos.CustomerRegisterDto;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
	
	private final CustomerService customerService;
	
	public CustomerController(CustomerService customerService) {
		super();
		this.customerService = customerService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerCustomer(@RequestBody CustomerRegisterDto customerRegisterDto){
		 Result result=  this.customerService.register(customerRegisterDto);
		 if(result.getSuccess()) {
			 return ResponseEntity.ok(result.getMessage());
		 }
		 else {
			 return ResponseEntity.badRequest().body(result.getMessage());
		 }
	}

}

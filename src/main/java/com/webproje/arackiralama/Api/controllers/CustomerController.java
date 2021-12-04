package com.webproje.arackiralama.Api.controllers;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import com.webproje.arackiralama.Business.abstracts.CustomerService;
import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Core.utilities.result.concretes.ErrorResult;
import com.webproje.arackiralama.Entity.dto.carRentalsDtos.CarRentalListDto;
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
	
	@GetMapping("/rentals/list")
	public ResponseEntity<?> listRentalRequests(@RequestParam Optional<Integer> pageSize,@RequestParam Optional<Integer> pageNum){
		 DataResult<List<CarRentalListDto>> result=  this.customerService.listRentalRequests(pageSize,pageNum);
		 if(result.getSuccess()) {
			 return ResponseEntity.ok(result.getData());
		 }
		 else {
			 return ResponseEntity.badRequest().body(result.getMessage());
		 }
	}
	
	@GetMapping("/rentals/list/{rentalId}")
	public ResponseEntity<?> listSingleRentalRequest(@PathVariable int rentalId){
		 DataResult<CarRentalListDto> result=  this.customerService.listSingleRentalRequest(rentalId);
		 if(result.getSuccess()) {
			 return ResponseEntity.ok(result.getData());
		 }
		 else {
			 return ResponseEntity.badRequest().body(result.getMessage());
		 }
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResult handleDuplicateError(DataIntegrityViolationException exceptions){
		return new ErrorResult("Duplicate error! There is already a user with these informations. Please check your email and identity number.");
	}
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResult handleEmptyDeleteRequest(EmptyResultDataAccessException exceptions){
		return new ErrorResult("Error: There is no such a rental request with that id. ");
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResult handleEntityNotFound(EntityNotFoundException exceptions){
		return new ErrorResult("Error: There is no such a rental request with that id.");
	}

}

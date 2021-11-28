package com.webproje.arackiralama.Api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.webproje.arackiralama.Core.utilities.result.concretes.ErrorResult ;
import com.webproje.arackiralama.Business.abstracts.CompanyService;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Entity.dto.CompanyAddDto;

@RestController
@RequestMapping("/api/company")
public class CompanyController {
	
	private final CompanyService companyService;

	@Autowired
	public CompanyController(CompanyService companyService) {
		super();
		this.companyService = companyService;
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> addCompany(@RequestBody CompanyAddDto companyAddDto){
		Result result=  this.companyService.addCompany(companyAddDto);
		 if(result.getSuccess()) {
			 return ResponseEntity.ok(result.getMessage());
		 }
		 else {
			 return ResponseEntity.badRequest().body(result.getMessage());
		 }
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResult handleDuplicateError(DataIntegrityViolationException exceptions){
		return new ErrorResult("Duplicate error! There is already a company with this name. Please change your name.");
	}
	
}

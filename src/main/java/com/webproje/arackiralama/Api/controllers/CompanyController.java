package com.webproje.arackiralama.Api.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.webproje.arackiralama.Core.utilities.result.concretes.ErrorResult ;

import com.webproje.arackiralama.Entity.dto.companyDtos.CompanyAddDto;
import com.webproje.arackiralama.Entity.dto.companyDtos.CompanyListDto;

import com.webproje.arackiralama.Business.abstracts.CompanyService;
import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;

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
	
	@GetMapping("/list")
	public ResponseEntity<?> listCompany(@RequestParam Optional<Integer> companyId,@RequestParam Optional<Integer> pageSize, @RequestParam Optional<Integer> pageNum){
		DataResult<List<CompanyListDto>> result = this.companyService.listCompany(companyId,pageSize,pageNum);
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
		return new ErrorResult("Duplicate error! There is already a company with this name. Please change the company name.");
	}
	
}

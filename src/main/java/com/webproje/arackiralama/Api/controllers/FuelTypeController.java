package com.webproje.arackiralama.Api.controllers;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.webproje.arackiralama.Business.abstracts.FuelTypeService;
import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Core.utilities.result.concretes.ErrorResult;
import com.webproje.arackiralama.Entity.concretes.FuelType;

@RestController
@RequestMapping(value= "/api/fueltype")
public class FuelTypeController {

	private final FuelTypeService fuelTypeService;

	@Autowired
	public FuelTypeController(FuelTypeService fuelTypeService) {
		super();
		this.fuelTypeService = fuelTypeService;
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> addFuelType(@RequestBody FuelType fuelType){
		Result result = this.fuelTypeService.addFuelType(fuelType);
		if(result.getSuccess()) {
			return ResponseEntity.ok(result.getMessage());
		}
		else {
			return ResponseEntity.badRequest().body(result.getMessage());
		}
	}
	
	@DeleteMapping("/delete/{fuelTypeId}")
	public ResponseEntity<?> deleteFuelType(@PathVariable int fuelTypeId){
		Result result = this.fuelTypeService.deleteFuelType(fuelTypeId);
		if(result.getSuccess()) {
			return ResponseEntity.ok(result.getMessage());
		}
		else {
			return ResponseEntity.badRequest().body(result.getMessage());
		}
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateFuelType(@RequestBody FuelType fuelType){
		Result result = this.fuelTypeService.updateFuelType(fuelType);
		if(result.getSuccess()) {
			return ResponseEntity.ok(result.getMessage());
		}
		else {
			return ResponseEntity.badRequest().body(result.getMessage());
		}
	}
	
	@GetMapping("/list")
	public ResponseEntity<?> updateFuelType(){
		DataResult<List<FuelType>> result = this.fuelTypeService.listFuelTypes();
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
		return new ErrorResult("Duplicate error! There is already a fuel type with these informations. Please check your email and identity number.");
	}
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResult handleEmptyDeleteRequest(EmptyResultDataAccessException exceptions){
		return new ErrorResult("Error: There is no such a fuel type with that id. ");
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResult handleEntityNotFound(EntityNotFoundException exceptions){
		return new ErrorResult("Error: There is no such a  fuel type with that id.");
	}
	
}

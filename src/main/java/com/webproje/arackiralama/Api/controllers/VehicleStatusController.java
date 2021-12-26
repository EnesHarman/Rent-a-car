package com.webproje.arackiralama.Api.controllers;

import java.util.List;

import javax.persistence.EntityNotFoundException;

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

import com.webproje.arackiralama.Business.abstracts.VehicleStatusService;
import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Core.utilities.result.concretes.ErrorResult;
import com.webproje.arackiralama.Entity.concretes.VehicleStatus;

@RestController
@RequestMapping(value="/api/vehiclestatus")
public class VehicleStatusController {

	private final VehicleStatusService vehicleStatusService;

	public VehicleStatusController(VehicleStatusService vehicleStatusService) {
		super();
		this.vehicleStatusService = vehicleStatusService;
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> addVehicleStatus(@RequestBody VehicleStatus vehicleStatus){
		Result result = this.vehicleStatusService.addVehicleStatus(vehicleStatus);
		if(result.getSuccess()) {
			return ResponseEntity.ok(result.getMessage());
		}
		else {
			return ResponseEntity.badRequest().body(result.getMessage());
		}
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateVehicleStatus(@RequestBody VehicleStatus vehicleStatus){
		Result result = this.vehicleStatusService.updateVehicleStatus(vehicleStatus);
		if(result.getSuccess()) {
			return ResponseEntity.ok(result.getMessage());
		}
		else {
			return ResponseEntity.badRequest().body(result.getMessage());
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteVehicleStatus(@PathVariable int id){
		Result result = this.vehicleStatusService.deleteVehicleStatus(id);
		if(result.getSuccess()) {
			return ResponseEntity.ok(result.getMessage());
		}
		else {
			return ResponseEntity.badRequest().body(result.getMessage());
		}
	}
	
	@GetMapping("/list")
	public ResponseEntity<?> listVehicleStatus(){
		DataResult<List<VehicleStatus>> result = this.vehicleStatusService.listVehicleStatus();
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
		return new ErrorResult("Duplicate error! There is already a vehicle status with these informations. Please check your email and identity number.");
	}
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResult handleEmptyDeleteRequest(EmptyResultDataAccessException exceptions){
		return new ErrorResult("Error: There is no such a vehicle status with that id. ");
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResult handleEntityNotFound(EntityNotFoundException exceptions){
		return new ErrorResult("Error: There is no such a vehicle status with that id.");
	}
}

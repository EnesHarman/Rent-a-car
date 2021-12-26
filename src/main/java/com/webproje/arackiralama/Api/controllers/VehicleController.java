package com.webproje.arackiralama.Api.controllers;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;


import org.springframework.beans.factory.annotation.Autowired;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.webproje.arackiralama.Business.abstracts.VehicleService;
import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Core.utilities.result.concretes.ErrorResult;
import com.webproje.arackiralama.Entity.dto.carRentalsDtos.CarRentalDto;
import com.webproje.arackiralama.Entity.dto.vehicleDtos.VehicleDto;


@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {

	private VehicleService vehicleService;

	@Autowired
	public VehicleController(VehicleService vehicleService) {
		super();
		this.vehicleService = vehicleService;
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> addVehicle(@RequestBody VehicleDto vehicleDto){
		Result result = this.vehicleService.addVehicle(vehicleDto);
		if(result.getSuccess()) {
			return ResponseEntity.ok(result.getMessage());
		}
		else {
			return ResponseEntity.badRequest().body(result.getMessage());
		}
	}
	
	@PutMapping("/update/{vehicleId}")
	public ResponseEntity<?> updateVehicle(@PathVariable int vehicleId, @RequestBody VehicleDto vehicleDto ){
		Result result = this.vehicleService.updateVehicle(vehicleId,vehicleDto);
		if(result.getSuccess()) {
			return ResponseEntity.ok(result.getMessage());
		}
		else {
			return ResponseEntity.badRequest().body(result.getMessage());
		}
	}
	
	@DeleteMapping("/delete/{vehicleId}")
	public ResponseEntity<?> deleteVehicle(@PathVariable int vehicleId){
		Result result = this.vehicleService.deleteVehicle(vehicleId);
		if(result.getSuccess()) {
			return ResponseEntity.ok(result.getMessage());
		}
		else {
			return ResponseEntity.badRequest().body(result.getMessage());
		}
	}
	
	@GetMapping("/list")
	public ResponseEntity<?> listVehicles(
			@RequestParam Optional<Integer> companyId,@RequestParam Optional<Integer> cityId, 
			@RequestParam Optional<Integer> pageSize, @RequestParam Optional<Integer> pageNum)
	{
		
		DataResult<List<VehicleDto>> result = this.vehicleService.listVehicles(companyId,cityId, pageSize, pageNum);
		if(result.getSuccess()) {
			return ResponseEntity.ok(result.getData());
		}
		else {
			return ResponseEntity.badRequest().body(result.getMessage());
		}
	} 
	
	@GetMapping("/details/{vehicleId}")
	public ResponseEntity<?> listSingleVehicle(@PathVariable Optional<Integer> vehicleId){
		DataResult<VehicleDto> result = this.vehicleService.listSingleVehicle(vehicleId);
		if(result.getSuccess()) {
			return ResponseEntity.ok(result.getData());
		}
		else {
			return ResponseEntity.badRequest().body(result.getMessage());
		}
	}
	
	@PostMapping("/rent")
	public ResponseEntity<?> rentACar(@RequestBody CarRentalDto carRentalDto){
		Result result = this.vehicleService.rentACar(carRentalDto);
		if(result.getSuccess()) {
			return ResponseEntity.ok(result.getMessage());
		}
		else {
			return ResponseEntity.badRequest().body(result.getMessage());
		}
	}
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResult handleEmptyDeleteRequest(EmptyResultDataAccessException exceptions){
		return new ErrorResult("Error: There is no such a vehicle  with that id. ");
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResult handleEntityNotFound(EntityNotFoundException exceptions){
		return new ErrorResult("Error: There is no such a vehicle with that id.");
	}
	
	
}

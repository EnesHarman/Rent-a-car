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

import com.webproje.arackiralama.Business.abstracts.CompanyManagerService;

import com.webproje.arackiralama.Business.abstracts.VehicleService;
import com.webproje.arackiralama.Business.constants.Messages;
import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Core.utilities.result.concretes.ErrorResult;
import com.webproje.arackiralama.Entity.dto.carRentalsDtos.CarRentalListDto;
import com.webproje.arackiralama.Entity.dto.companyManagerDtos.CompanyManagerRegisterDto;
import com.webproje.arackiralama.Entity.dto.vehicleDtos.VehicleDto;

@RestController
@RequestMapping("/api/companymanager")
public class CompanyManagerController {
	
	private final CompanyManagerService companyManagerService;
	private final VehicleService vehicleService;

	@Autowired
	public CompanyManagerController(CompanyManagerService companyManagerService, VehicleService vehicleService) {
		super();
		this.companyManagerService = companyManagerService;
		this.vehicleService = vehicleService;
	}
	
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody CompanyManagerRegisterDto companyManagerRegisterDto){
		Result result = this.companyManagerService.register(companyManagerRegisterDto);
		if(result.getSuccess()) {
			return ResponseEntity.ok(result.getMessage());
		}
		else {

			return ResponseEntity.badRequest().body(result.getMessage());
		}
	}
	
	@GetMapping("/vehicles/list")
	public ResponseEntity<?> listCompanyVehicles(@RequestParam Optional<Integer> pageSize,@RequestParam Optional<Integer> pageNum ){
		DataResult<List<VehicleDto>> vehicles = this.vehicleService.listVehiclesForManager(pageSize, pageNum);
		if(vehicles.getSuccess()) {
			return ResponseEntity.ok(vehicles.getData());
		}
		else {
			return ResponseEntity.internalServerError().body(Messages.InternalServerError);
		}
	}
	
	@GetMapping("/rentals/list")
	public ResponseEntity<?> listRentalRequests(@RequestParam Optional<Integer> pageSize,@RequestParam Optional<Integer> pageNum){
		DataResult<List<CarRentalListDto>> result = this.companyManagerService.listCarRentalRequests(pageSize, pageNum);
		if(result.getSuccess()) {
			return ResponseEntity.ok(result.getData());
		}
		else {
			return ResponseEntity.internalServerError().body(Messages.InternalServerError);
		}
	}
	
	@DeleteMapping("/rentals/{requestId}/reject")
	public ResponseEntity<?> rejectRentalRequest(@PathVariable int requestId){
		Result result = this.companyManagerService.rejectRentalRequest(requestId);
		if(result.getSuccess()) {
			return ResponseEntity.ok(result.getMessage());
		}
		else {
			return ResponseEntity.badRequest().body(result.getMessage());
		}
	}
	
	@PutMapping("/rentals/{requestId}/confirm")
	public ResponseEntity<?> confirmRentalRequest(@PathVariable int requestId){
		Result result = this.companyManagerService.confirmRentalRequest(requestId);
		if(result.getSuccess()) {
			return ResponseEntity.ok(result.getMessage());
		}
		else {
			return ResponseEntity.badRequest().body(result.getMessage());
		}
	}
	
	@PutMapping("/rentals/{rentalId}/return")
	public ResponseEntity<?> returnVehicle(@PathVariable int rentalId){
		Result result = this.companyManagerService.returnVehicle(rentalId);
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
		return new ErrorResult("Error: There is no such a rental request with that id. ");
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResult handleEntityNotFound(EntityNotFoundException exceptions){
		return new ErrorResult("Error: There is no such a rental request with that id.");
	}
			
}

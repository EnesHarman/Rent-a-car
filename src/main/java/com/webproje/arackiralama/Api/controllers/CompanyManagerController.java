package com.webproje.arackiralama.Api.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webproje.arackiralama.Business.abstracts.CompanyManagerService;

import com.webproje.arackiralama.Business.abstracts.VehicleService;
import com.webproje.arackiralama.Business.constants.Messages;
import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
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
	
	@GetMapping("/vehicles/list")
	public ResponseEntity<?> listCompanyVehicles(){
		DataResult<List<VehicleDto>> vehicles = this.vehicleService.listVehiclesForManager();
		if(vehicles.getSuccess()) {
			return ResponseEntity.ok(vehicles.getData());
		}
		else {
			return ResponseEntity.internalServerError().body(Messages.InternalServerError);
		}
	}
	
	
}

package com.webproje.arackiralama.Api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webproje.arackiralama.Business.abstracts.VehicleService;
import com.webproje.arackiralama.Entity.dto.VehicleDto;

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
	public ResponseEntity<?> AddVehicle(@RequestBody VehicleDto vehicleDto){
		
	}
	
}

package com.webproje.arackiralama.Api.controllers;

import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webproje.arackiralama.Business.abstracts.VehicleService;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
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
	public ResponseEntity<?> AddVehicle(@RequestBody VehicleDto vehicleDto){
		Result result = this.vehicleService.addVehicle(vehicleDto);
		if(result.getSuccess()) {
			return ResponseEntity.ok(result.getMessage());
		}
		else {
			return ResponseEntity.badRequest().body(result.getMessage());
		}
	}
	
	@PutMapping("/update/{vehicleId}")
	public ResponseEntity<?> UpdateVehicle(@PathVariable int vehicleId,@RequestBody VehicleDto vehicleDto){
		Result result = this.vehicleService.updateVehicle(vehicleId,vehicleDto);
		if(result.getSuccess()) {
			return ResponseEntity.ok(result.getMessage());
		}
		else {
			return ResponseEntity.badRequest().body(result.getMessage());
		}
	}
	
}

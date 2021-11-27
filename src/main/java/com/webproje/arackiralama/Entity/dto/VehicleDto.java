package com.webproje.arackiralama.Entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDto {

	private String brand;
	
	private String model;
	
	private double deposit;
	
	private int kilometerLimit;
	
	private double price;
	
	private String imgUrl;
	
	private String fuelType;
	
	private String gearType;
	
	private String vehicleStatus;

}

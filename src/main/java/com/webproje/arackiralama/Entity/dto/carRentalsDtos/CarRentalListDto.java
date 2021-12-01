package com.webproje.arackiralama.Entity.dto.carRentalsDtos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CarRentalListDto {
	private int rentalRequestId;
	
	private int rentalPeriod;
	
	private Date rentalDate;
	
	private  String customerName;
	
	private String customerSurname;
	
	private boolean isApproved;

	private String customerNumber;

	private int vehicleId;

	private String ImgUrl;
	
	private String brand;
	
	private String model;
	
}

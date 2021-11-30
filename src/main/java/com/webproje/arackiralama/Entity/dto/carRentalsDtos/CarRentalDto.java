package com.webproje.arackiralama.Entity.dto.carRentalsDtos;

import java.util.Date;

import com.webproje.arackiralama.Entity.concretes.Company;
import com.webproje.arackiralama.Entity.concretes.Customer;
import com.webproje.arackiralama.Entity.concretes.Vehicle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarRentalDto {

	private int rentalPeriod;
	
	private Date rentalDate;
	
	private int companyId;

	private int vehicleId;
	
}

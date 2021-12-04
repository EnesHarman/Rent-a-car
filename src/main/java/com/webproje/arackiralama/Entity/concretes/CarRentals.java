package com.webproje.arackiralama.Entity.concretes;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="car_rentals")
@Entity
public class CarRentals {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name = "rental_period")
	private int rentalPeriod;
	
	@Column(name="rental_date")
	private Date rentalDate;
	
	@Column(name="is_approved")
	private boolean isApproved;

	@ManyToOne
	@JoinColumn(name= "customer_id")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name="company_id")
	private Company company;
	
	@ManyToOne
	@JoinColumn(name="vehicle_id")
	private Vehicle vehicle;
	
	public boolean getIsApproved() {
		return this.isApproved;
	}
}

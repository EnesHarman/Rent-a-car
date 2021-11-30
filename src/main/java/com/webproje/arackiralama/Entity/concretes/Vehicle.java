package com.webproje.arackiralama.Entity.concretes;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="vehicles")
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","carRentals"})
public class Vehicle {
	
	public Vehicle(int id) {
		this.id = id;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="brand", nullable = false, columnDefinition = "nvarchar(50)")
	private String brand;
	
	@Column(name="model", nullable = false,columnDefinition = "nvarchar(50)")
	private String model;
	
	@Column(name="deposit", nullable = false)
	private double deposit;
	
	@Column(name="kilometer_limit")
	private int kilometerLimit;
	
	@Column(name="price", nullable = false)
	private double price;
	
	@Column(name="img_url", nullable = false)
	private String ImgUrl;
	
	@ManyToOne
	@JoinColumn(name = "fuel_type")
	private FuelType fuelType;
	
	@ManyToOne
	@JoinColumn(name="gear_type")
	private GearType gearType;
	
	@ManyToOne
	@JoinColumn(name="vehicle_status_id")
	private VehicleStatus vehicleStatus;
	
	@ManyToOne
	@JoinColumn(name="company_id")
	private Company company;
	
	@OneToMany(mappedBy = "vehicle")
	private List<CarRentals> carRentals;
}

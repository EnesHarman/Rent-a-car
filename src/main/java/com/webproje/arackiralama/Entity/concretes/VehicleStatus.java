package com.webproje.arackiralama.Entity.concretes;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="vehicle_status")
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","vehicles"})
public class VehicleStatus {
	
	public VehicleStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public VehicleStatus(int id) {
		this.id = id;

	}
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="name", nullable = false,columnDefinition ="varchar(10)", unique = true)
	private String name;
	
	@OneToMany(mappedBy = "vehicleStatus")
	private List<Vehicle> vehicles;
}

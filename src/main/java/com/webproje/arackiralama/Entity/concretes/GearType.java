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
@Table(name="gear_types")
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","vehicles"})
public class GearType {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="name", nullable = false,columnDefinition ="varchar(25)", unique = true)
	private String name;
	
	@OneToMany(mappedBy = "gearType")
	private List<Vehicle> vehicles;
	
	public GearType(int id) {
		this.id = id;
	}
}


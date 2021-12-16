package com.webproje.arackiralama.Entity.concretes;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="companies")
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","managers","carRentals"})
public class Company {
	
	public Company(int id) {
		this.id = id;
	}
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="name", nullable = false)
	private String companyName;
	
	@Column(name="adress" , nullable = false ,columnDefinition = "text")
	private String adress;
	
	@Column(name="vehicle_number")
	private int vehicleNumber = 0;
	
	@Column(name="point")
	private float point = 0f;
	
	@Column(name="site_url" , nullable = false)
	private String siteUrl;
	
	@Column(name="phone_number" , nullable = false, columnDefinition = "varchar(15)")
	private String phoneNumber;
	
	@Column(name="manager_email",nullable =  false, columnDefinition = "nvarchar(90)", unique = true)
	private String managerEmail;
	
	@ManyToOne
	@JoinColumn(name="city_id")
	private City city;
	
	@OneToMany(mappedBy = "company",cascade = CascadeType.REMOVE)
	private List<Vehicle> vehicles;
	
	@OneToOne(mappedBy = "company", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private CompanyManager manager;
	
	@OneToMany(mappedBy = "company", cascade = CascadeType.REMOVE)
	private List<CarRentals> carRentals;
	
}

package com.webproje.arackiralama.Entity.concretes;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.webproje.arackiralama.Core.entity.concretes.AppUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="customers")
@Entity
public class Customer {
	
	public Customer(int id) {
		this.id = id;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="name", columnDefinition = "varchar(50)",nullable = false)
	private String name;
	
	@Column(name="surname", columnDefinition = "varchar(50)",nullable = false)
	private String surname;
	
	@Column(name="identity_number", columnDefinition = "varchar(11)" ,unique =  true,nullable = false)
	private String identityNumber;
	
	@Column(name="phone_number", columnDefinition = "varchar(15)",nullable = false)
	private String phoneNumber;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="user_id")
	private AppUser user;
	
	@OneToMany(mappedBy = "customer", cascade =  CascadeType.REMOVE)
	private List<CarRentals> carRentals;
}

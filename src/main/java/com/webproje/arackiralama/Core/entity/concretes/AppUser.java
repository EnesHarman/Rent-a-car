package com.webproje.arackiralama.Core.entity.concretes;




import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

import com.webproje.arackiralama.Entity.concretes.CompanyManager;
import com.webproje.arackiralama.Entity.concretes.Customer;
import com.webproje.arackiralama.Entity.concretes.SystemManager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","systemManager","companyManager","customer"})
public class AppUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int userId;
	
	@Column(name="email", unique = true,columnDefinition = "nvarchar(255)",nullable = false) 
	@Email
	@NotBlank
	@NotNull
	private String email;
	
	@Column(name="password",nullable = false)
	@NotBlank
	@NotNull
	private String password;
	
	@ManyToOne
	@JoinColumn(name="role_id")
	private Role role;
	
	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
	private SystemManager systemManager;
	
	@OneToOne(mappedBy = "appUser", fetch = FetchType.LAZY)
	private CompanyManager companyManager;
	
	@OneToOne(mappedBy = "user")
	private Customer customer;
	
	
}

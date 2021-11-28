package com.webproje.arackiralama.Entity.concretes;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name="cities")
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","companies"})
public class City {
	
	public City(int id) {
		this.id = id;
	}
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="name", nullable = false, columnDefinition = "nvarchar(20)")
	private String name;
	
	@OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
	List<Company> companies;
}

package com.webproje.arackiralama.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webproje.arackiralama.Entity.concretes.FuelType;

public interface FuelTypeRepository extends JpaRepository<FuelType, Integer>{
	FuelType getByName(String name);
}

package com.webproje.arackiralama.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webproje.arackiralama.Entity.concretes.GearType;

public interface GearTypeRepository extends JpaRepository<GearType, Integer>{
	GearType getByName(String name);
}

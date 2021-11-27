package com.webproje.arackiralama.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webproje.arackiralama.Entity.concretes.VehicleStatus;

public interface VehicleStatusRepository extends JpaRepository<VehicleStatus, Integer> {

	VehicleStatus getByName(String name);

}

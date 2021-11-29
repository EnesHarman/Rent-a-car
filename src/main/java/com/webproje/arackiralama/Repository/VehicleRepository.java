package com.webproje.arackiralama.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.webproje.arackiralama.Entity.concretes.Vehicle;
import com.webproje.arackiralama.Entity.dto.vehicleDtos.VehicleDto;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer>{

	@Query("Select new com.webproje.arackiralama.Entity.dto.vehicleDtos.VehicleDto "
			+ "(v.id,v.brand,v.model,v.deposit,v.kilometerLimit,v.price,v.ImgUrl,f.name,g.name,vs.name)"
			+ "From Vehicle v Inner Join v.fuelType f Inner Join v.gearType g Inner Join v.vehicleStatus vs   where v.company.id =:companyId")
	List<VehicleDto> listVehiclesForManager(int companyId);
}

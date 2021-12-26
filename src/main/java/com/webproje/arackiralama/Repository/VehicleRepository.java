package com.webproje.arackiralama.Repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.webproje.arackiralama.Entity.concretes.Vehicle;
import com.webproje.arackiralama.Entity.dto.vehicleDtos.VehicleDto;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer>{

	@Query("Select new com.webproje.arackiralama.Entity.dto.vehicleDtos.VehicleDto "
			+ "(v.id,v.brand,v.model,v.deposit,v.kilometerLimit,v.price,v.ImgUrl,f.name,g.name,vs.name,c.id)"
			+ "From Vehicle v Inner Join v.fuelType f Inner Join v.gearType g Inner Join v.vehicleStatus vs  Inner Join v.company c  where v.company.id =:companyId")
	List<VehicleDto> listVehiclesByCompanyId(int companyId, Pageable pageable); //For company manager
	
	@Query("Select new com.webproje.arackiralama.Entity.dto.vehicleDtos.VehicleDto "
			+ "(v.id,v.brand,v.model,v.deposit,v.kilometerLimit,v.price,v.ImgUrl,f.name,g.name,vs.name,c.id)"
			+ "From Vehicle v Inner Join v.fuelType f Inner Join v.gearType g Inner Join v.vehicleStatus vs  Inner Join v.company c  where v.company.id =:companyId and v.vehicleStatus.id =:vehicleStatus")
	List<VehicleDto> listVehiclesByCompanyId(int companyId, Pageable pageable,int vehicleStatus);
	
	@Query("Select new com.webproje.arackiralama.Entity.dto.vehicleDtos.VehicleDto "
			+ "(v.id,v.brand,v.model,v.deposit,v.kilometerLimit,v.price,v.ImgUrl,f.name,g.name,vs.name,c.id)"
			+ "From Vehicle v Inner Join v.fuelType f Inner Join v.gearType g Inner Join v.vehicleStatus vs  Inner Join v.company c  where v.vehicleStatus.id =:vehicleStatus and c.city.id =:cityId")
	List<VehicleDto> listVehicles(int cityId,Pageable pageable,int vehicleStatus);
	
	@Query("Select new com.webproje.arackiralama.Entity.dto.vehicleDtos.VehicleDto "
			+ "(v.id,v.brand,v.model,v.deposit,v.kilometerLimit,v.price,v.ImgUrl,f.name,g.name,vs.name,c.id)"
			+ "From Vehicle v Inner Join v.fuelType f Inner Join v.gearType g Inner Join v.vehicleStatus vs  Inner Join v.company c  where v.vehicleStatus.id =:vehicleStatus")
	List<VehicleDto> listVehicles(Pageable pageable,int vehicleStatus);
	
	@Query("Select new com.webproje.arackiralama.Entity.dto.vehicleDtos.VehicleDto "
			+ "(v.id,v.brand,v.model,v.deposit,v.kilometerLimit,v.price,v.ImgUrl,f.name,g.name,vs.name,c.id)"
			+ "From Vehicle v Inner Join v.fuelType f Inner Join v.gearType g Inner Join v.vehicleStatus vs  Inner Join v.company c  where v.id =:vehicleId")
	VehicleDto listSingleVehicle(int vehicleId);
}

package com.webproje.arackiralama.Repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.webproje.arackiralama.Entity.concretes.CarRentals;
import com.webproje.arackiralama.Entity.dto.carRentalsDtos.CarRentalListDto;

public interface CarRentalRepository extends JpaRepository<CarRentals, Integer>{
	
	@Query("Select new com.webproje.arackiralama.Entity.dto.carRentalsDtos.CarRentalListDto"
			+ " (cr.id ,cr.rentalPeriod, cr.rentalDate, cu.name, cu.surname,cr.isApproved, cu.phoneNumber, ve.id, ve.ImgUrl,"
			+ "ve.brand, ve.model) From CarRentals cr Inner Join cr.customer cu Inner Join cr.vehicle ve where cr.company.id =:companyId")
	
	List<CarRentalListDto> getRentalRequestsByCompanyId(int companyId, Pageable pageable);
	
	@Query("Select new com.webproje.arackiralama.Entity.dto.carRentalsDtos.CarRentalListDto"
			+ " (cr.id ,cr.rentalPeriod, cr.rentalDate, cu.name, cu.surname,cr.isApproved, cu.phoneNumber, ve.id, ve.ImgUrl,"
			+ "ve.brand, ve.model) From CarRentals cr Inner Join cr.customer cu Inner Join cr.vehicle ve where cr.customer.id =:customerId")
	
	List<CarRentalListDto> getRentalRequestsByCustomerId(int customerId, Pageable pageable);

	@Query("Select new com.webproje.arackiralama.Entity.dto.carRentalsDtos.CarRentalListDto"
			+ " (cr.id ,cr.rentalPeriod, cr.rentalDate, cu.name, cu.surname,cr.isApproved, cu.phoneNumber, ve.id, ve.ImgUrl,"
			+ "ve.brand, ve.model) From CarRentals cr Inner Join cr.customer cu Inner Join cr.vehicle ve where cr.id =:rentalId")
	CarRentalListDto listSingleRentalRequest(int rentalId);
}

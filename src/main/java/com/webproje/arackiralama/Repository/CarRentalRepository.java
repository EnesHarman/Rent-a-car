package com.webproje.arackiralama.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webproje.arackiralama.Entity.concretes.CarRentals;

public interface CarRentalRepository extends JpaRepository<CarRentals, Integer>{

}

package com.webproje.arackiralama.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webproje.arackiralama.Entity.concretes.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer>{

}

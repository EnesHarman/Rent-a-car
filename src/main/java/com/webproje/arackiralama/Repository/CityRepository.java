package com.webproje.arackiralama.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webproje.arackiralama.Entity.concretes.City;

public interface CityRepository extends JpaRepository<City, Integer>{

}

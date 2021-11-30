package com.webproje.arackiralama.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webproje.arackiralama.Entity.concretes.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	Customer getByUser_UserId(int userId);

}

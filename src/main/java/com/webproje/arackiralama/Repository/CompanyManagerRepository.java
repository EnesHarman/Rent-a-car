package com.webproje.arackiralama.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webproje.arackiralama.Entity.concretes.CompanyManager;

public interface CompanyManagerRepository extends JpaRepository<CompanyManager, Integer>{
	CompanyManager getByAppUser_UserId(int UserId);
}

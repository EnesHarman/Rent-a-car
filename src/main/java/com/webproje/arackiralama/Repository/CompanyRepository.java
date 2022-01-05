package com.webproje.arackiralama.Repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.webproje.arackiralama.Entity.concretes.Company;
import com.webproje.arackiralama.Entity.dto.companyDtos.CompanyListDto;

public interface CompanyRepository extends JpaRepository<Company, Integer>{

	@Query("Select new com.webproje.arackiralama.Entity.dto.companyDtos.CompanyListDto"
			+ "(c.id, c.companyName,c.adress,c.point,c.siteUrl,c.phoneNumber,ci.name,m.appUser.email) From Company c Inner Join c.city ci Inner Join c.manager m Where c.id=:companyId")
	CompanyListDto listSingCompany(int companyId);
	
	@Query("Select new com.webproje.arackiralama.Entity.dto.companyDtos.CompanyListDto"
			+ "(c.id,c.companyName,c.adress,c.point,c.siteUrl,c.phoneNumber,ci.name,m.appUser.email) From Company c Inner Join c.city ci Inner Join c.manager m ")
	List<CompanyListDto> listAllCompanies(Pageable pageable);


}

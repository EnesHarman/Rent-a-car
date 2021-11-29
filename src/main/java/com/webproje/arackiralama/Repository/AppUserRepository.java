package com.webproje.arackiralama.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webproje.arackiralama.Core.entity.concretes.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Integer>{

	AppUser getByEmail(String email);
}

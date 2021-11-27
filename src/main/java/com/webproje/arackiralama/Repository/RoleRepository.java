package com.webproje.arackiralama.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webproje.arackiralama.Core.entity.concretes.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{

}

package com.webproje.arackiralama.Business.concretes;

import com.webproje.arackiralama.Core.entity.concretes.AppUser;
import com.webproje.arackiralama.Core.entity.concretes.Role;
import com.webproje.arackiralama.Repository.AppUserRepository;
import io.swagger.v3.oas.annotations.Parameter;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AppUserManagerTest {

    @Mock AppUserRepository appUserRepository;
    AppUserManager appUserManager;
    @Mock
    BCryptPasswordEncoder passwordEncoder;



    @BeforeEach
    void init(){
        this.appUserManager = new AppUserManager(appUserRepository,this.passwordEncoder);
    }


    @Test
    void testSaveAppUser() {
        //GIVEN
        String userPassword = "123456";

        AppUser appUser = new AppUser();
        appUser.setEmail("enes@gmail.com");
        appUser.setPassword(userPassword);
        appUser.setRole(new Role(3,"ROLE_COMPANY_MANAGER"));

        //WHEN
        this.appUserManager.saveUser(appUser);

        //THEN
        verify(passwordEncoder).encode(userPassword);
        verify(appUserRepository).save(appUser);
    }

    @Test
    void testGetUserByMail(){
        //GIVEN
        String email = "enes@gmail.com";
        //WHEN
        this.appUserManager.getUserByEmail(email);
        //THEN
        verify(appUserRepository).getByEmail(email);
    }
}
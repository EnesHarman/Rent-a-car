package com.webproje.arackiralama.Business.concretes;

import com.webproje.arackiralama.Repository.CityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CityManagerTest {

    @Mock
    CityRepository cityRepository;

    @InjectMocks
    CityManager underTest;

    @Test
    void testListCities() {
        //GIVEN
        //WHEN
        this.underTest.listCities();
        //THEN
        verify(cityRepository).findAll();
    }
}
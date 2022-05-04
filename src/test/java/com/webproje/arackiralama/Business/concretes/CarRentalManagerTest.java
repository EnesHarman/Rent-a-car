package com.webproje.arackiralama.Business.concretes;

import com.webproje.arackiralama.Core.entity.concretes.AppUser;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Entity.concretes.CarRentals;
import com.webproje.arackiralama.Entity.concretes.Company;
import com.webproje.arackiralama.Entity.concretes.Customer;
import com.webproje.arackiralama.Entity.concretes.Vehicle;
import com.webproje.arackiralama.Repository.CarRentalRepository;
import com.webproje.arackiralama.Repository.VehicleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CarRentalManagerTest {

    @Mock
    CarRentalRepository carRentalRepository;
    @Mock
    VehicleRepository vehicleRepository;
    @InjectMocks //Mockları alıp bunun için parametre olarak vermeyi sağlar
    CarRentalManager underTest;

    @Captor
    ArgumentCaptor<CarRentals> carRentalsArgumentCaptor;

    @BeforeEach
    void setUp() {
        //underTest = new CarRentalManager(carRentalRepository,vehicleRepository);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testAddCarRentalRequest() {
        //GIVEN
        AppUser appUser = new AppUser();
        appUser.setEmail("enes@gmail.com");

        Customer customer = new Customer();
        customer.setUser(appUser);

        Company company = new Company();
        company.setCompanyName("Company!");

        CarRentals rentalRequest = new CarRentals();
        rentalRequest.setCustomer(customer);
        rentalRequest.setId(1);
        rentalRequest.setCompany(company);

        //WHEN
        Result result=  this.underTest.addCarRentalRequest(rentalRequest);

        //THEN
        verify(carRentalRepository).save(rentalRequest);
        assertThat(result.getSuccess()).isEqualTo(true);
    }

    @Test
    void testGetRentalRequestsByCompanyId() {
        int companyId = 1;
        Pageable pageable = PageRequest.of(1,2);

        Result result=this.underTest.getRentalRequestsByCompanyId(companyId,pageable);

        verify(carRentalRepository).getRentalRequestsByCompanyId(companyId,pageable);
        assertThat(result.getSuccess()).isEqualTo(true);
    }

    @Test
    void testDeleteRentalRequestById() {

        //GIVEN
        int companyId=1;
        int requestId=1;

        Company company = new Company();
        company.setId(companyId);

        CarRentals carRental = new CarRentals();
        carRental.setApproved(false);
        carRental.setCompany(company);

        when(carRentalRepository.getById(any())).thenReturn(carRental);

        //WHEN
        Result result = this.underTest.deleteRentalRequestById(requestId,companyId);

        //THEN
        verify(carRentalRepository).getById(companyId);
        verify(carRentalRepository).deleteById(requestId);
        assertThat(result.getSuccess()).isEqualTo(true);

    }

    @Test
    void testConfirmRentalRequestById() {
        //GIVEN
        int companyId=1;
        int requestId=1;

        Company company = new Company();
        company.setId(companyId);

        Vehicle vehicle = new Vehicle();
        vehicle.setId(1);

        CarRentals carRental = new CarRentals();
        carRental.setApproved(false);
        carRental.setCompany(company);
        carRental.setVehicle(vehicle);

        when(carRentalRepository.getById(any())).thenReturn(carRental);
        when(vehicleRepository.getById(any())).thenReturn(vehicle);

        //WHEN
        this.underTest.confirmRentalRequestById(companyId,requestId);

        //THEN
        verify(carRentalRepository).getById(companyId);
        verify(carRentalRepository).save(carRental);
        verify(vehicleRepository).save(vehicle);
    }

    @Test
    void testReturnVehicle() {
        //GIVEN
        int rentalId=1;
        int companyId=1;

        Company company = new Company();
        company.setId(companyId);

        Vehicle vehicle = new Vehicle();
        vehicle.setId(1);

        CarRentals carRental = new CarRentals();
        carRental.setApproved(false);
        carRental.setCompany(company);
        carRental.setVehicle(vehicle);

        when(carRentalRepository.getById(any())).thenReturn(carRental);
        when(vehicleRepository.getById(any())).thenReturn(vehicle);
        //WHEN
        this.underTest.returnVehicle(rentalId,companyId);

        //THEN
        verify(carRentalRepository).getById(companyId);
        verify(carRentalRepository).deleteById(rentalId);
        verify(vehicleRepository).save(vehicle);

    }

    @Test
    void testGetCustomerEmailByRequestId() {
        int rentalId = 1;

        AppUser appUser = new AppUser();
        appUser.setEmail("enes@gmail.com");

        Customer customer = new Customer();
        customer.setUser(appUser);

        CarRentals carRental = new CarRentals();
        carRental.setId(rentalId);
        carRental.setCustomer(customer);

        when(carRentalRepository.getById(any())).thenReturn(carRental);

        this.underTest.getCustomerEmailByRequestId(rentalId);

        verify(carRentalRepository).getById(any());
    }
}
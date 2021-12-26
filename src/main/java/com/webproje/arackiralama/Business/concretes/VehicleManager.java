package com.webproje.arackiralama.Business.concretes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import com.webproje.arackiralama.Business.abstracts.CarRentalService;
import com.webproje.arackiralama.Business.abstracts.CompanyManagerService;
import com.webproje.arackiralama.Business.abstracts.CompanyService;
import com.webproje.arackiralama.Business.abstracts.CustomerService;
import com.webproje.arackiralama.Business.abstracts.FuelTypeService;
import com.webproje.arackiralama.Business.abstracts.GearTypeService;
import com.webproje.arackiralama.Business.abstracts.VehicleService;
import com.webproje.arackiralama.Business.abstracts.VehicleStatusService;
import com.webproje.arackiralama.Business.constants.Messages;

import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Core.utilities.result.concretes.ErrorDataResult;
import com.webproje.arackiralama.Core.utilities.result.concretes.ErrorResult;
import com.webproje.arackiralama.Core.utilities.result.concretes.SuccessDataResult;
import com.webproje.arackiralama.Core.utilities.result.concretes.SuccessResult;
import com.webproje.arackiralama.Entity.concretes.CarRentals;
import com.webproje.arackiralama.Entity.concretes.Company;

import com.webproje.arackiralama.Entity.concretes.Customer;
import com.webproje.arackiralama.Entity.concretes.FuelType;
import com.webproje.arackiralama.Entity.concretes.GearType;
import com.webproje.arackiralama.Entity.concretes.Vehicle;
import com.webproje.arackiralama.Entity.concretes.VehicleStatus;
import com.webproje.arackiralama.Entity.dto.carRentalsDtos.CarRentalDto;
import com.webproje.arackiralama.Entity.dto.vehicleDtos.VehicleDto;

import com.webproje.arackiralama.Repository.VehicleRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VehicleManager implements VehicleService{

	private final VehicleRepository vehicleRepository;
	
	private final GearTypeService  gearTypeService;
	private final FuelTypeService fuelTypeService;
	private final VehicleStatusService vehicleStatusService;
	private final CompanyManagerService companyManagerService;
	private final CarRentalService carRentalService;
	private final CustomerService customerService;
	private final CompanyService companyService;
	
	@Autowired
	public VehicleManager(VehicleRepository vehicleRepository, GearTypeService gearTypeService,
				FuelTypeService fuelTypeService, VehicleStatusService vehicleStatusService,CompanyManagerService companyManagerService
				,CarRentalService carRentalService,CustomerService customerService,CompanyService companyService) {
			super();
			this.vehicleRepository = vehicleRepository;
			this.companyManagerService = companyManagerService;
			this.gearTypeService = gearTypeService;
			this.fuelTypeService = fuelTypeService;
			this.vehicleStatusService = vehicleStatusService;
			this.carRentalService = carRentalService;
			this.customerService = customerService;
			this.companyService = companyService;
	}
	
	@Override
	public Result addVehicle(VehicleDto vehicleDto) {
		String managerEmail =  SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		
		DataResult<VehicleStatus> vehicleStatusResult = this.vehicleStatusService.getByName(vehicleDto.getVehicleStatus());
		if(!vehicleStatusResult.getSuccess()) {
			return new ErrorResult(vehicleStatusResult.getMessage());
		}

		DataResult<GearType> gearTypeResult = this.gearTypeService.getByName(vehicleDto.getGearType());
		if(!gearTypeResult.getSuccess()) {
			return new ErrorResult(gearTypeResult.getMessage());
		}

		DataResult<FuelType> fuelTypeResult = this.fuelTypeService.getByName(vehicleDto.getFuelType());
		if(!fuelTypeResult.getSuccess()) {
			return new ErrorResult(fuelTypeResult.getMessage());
		}
		
		Vehicle vehicle = new Vehicle();
		vehicle.setBrand(vehicleDto.getBrand());
		vehicle.setModel(vehicleDto.getModel());
		vehicle.setDeposit(vehicleDto.getDeposit());
		vehicle.setKilometerLimit(vehicleDto.getKilometerLimit());
		vehicle.setPrice(vehicleDto.getPrice());
		vehicle.setImgUrl(vehicleDto.getImgUrl());
		vehicle.setGearType(gearTypeResult.getData());
		vehicle.setFuelType(fuelTypeResult.getData());
		vehicle.setVehicleStatus(vehicleStatusResult.getData());;
		
		Company company =this.companyService.getById(this.companyManagerService.getCompanyIdByManagerEmail(managerEmail).getData()).getData();
		vehicle.setCompany(company);
		company.setVehicleNumber(company.getVehicleNumber()+1);

		this.vehicleRepository.save(vehicle);
		this.companyService.save(company);
		log.info("A company manager with "+ managerEmail + " has added a car to the system.");
		return new SuccessResult(Messages.vehicleAdded);
	}
	
	@Override
	public Result updateVehicle(int vehicleId, VehicleDto vehicleDto) {
		String managerEmail =  SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		int companyId = this.companyManagerService.getCompanyIdByManagerEmail(managerEmail).getData();
		
		Vehicle  vehicle = this.vehicleRepository.getById(vehicleId);
		if(vehicle.getCompany().getId() != companyId) {
			return new ErrorResult(Messages.vehicleNotUpdated);
		}
		vehicle.setBrand(vehicleDto.getBrand());
		vehicle.setModel(vehicleDto.getModel());
		vehicle.setDeposit(vehicleDto.getDeposit());
		vehicle.setKilometerLimit(vehicleDto.getKilometerLimit());
		vehicle.setPrice(vehicleDto.getPrice());
		vehicle.setImgUrl(vehicleDto.getImgUrl());
		vehicle.getFuelType().setName(vehicleDto.getFuelType());
		vehicle.getGearType().setName(vehicleDto.getGearType());
		vehicle.getVehicleStatus().setName(vehicleDto.getVehicleStatus());
		this.vehicleRepository.save(vehicle);
		
		log.info("A company manager with "+ managerEmail + " has updated a car.");
		return new SuccessResult(Messages.vehicleUpdated);
	}

	@Override
	public DataResult<List<VehicleDto>> listVehiclesForManager(Optional<Integer> pageSize,
			Optional<Integer> pageNum) {
		
		int _pageSize = pageSize.isPresent() && pageSize.get()<20 && pageSize.get()>10 ?pageSize.get() : 10;
		int _pageNum = pageNum.isPresent() && pageNum.get()>0 ? pageNum.get(): 1;
		Pageable pageable = PageRequest.of(_pageNum-1, _pageSize);
		
		String managerEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		int companyId = this.companyManagerService.getCompanyIdByManagerEmail(managerEmail).getData();
		
		List<VehicleDto> vehicles = this.vehicleRepository.listVehiclesByCompanyId(companyId,pageable);
		log.info("A company manager with "+ managerEmail + " has listed cars.");
		return new SuccessDataResult<List<VehicleDto>>(vehicles);
	}

	@Override
	public Result deleteVehicle(int vehicleId) {
		String managerEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		int companyId = this.companyManagerService.getCompanyIdByManagerEmail(managerEmail).getData();
		Vehicle  vehicle = this.vehicleRepository.getById(vehicleId);
		if(vehicle== null) {
			return new ErrorResult(Messages.vehicleStatusNotFound);
		}
		if(vehicle.getCompany().getId() != companyId) {
			return new ErrorResult(Messages.vehicleNotUpdated);
		}
		this.vehicleRepository.deleteById(vehicleId);
		log.info("A company manager with "+ managerEmail + " has deleted a car.");
		return new SuccessResult(Messages.vehicleDeleted);
	}

	@Override
	public DataResult<List<VehicleDto>> listVehicles(Optional<Integer> companyId,Optional<Integer> cityId, Optional<Integer> pageSize,
			Optional<Integer> pageNum) {
		int vehicleStatusRentableId = 1;
		
		int _pageSize = pageSize.isPresent() && pageSize.get()<20 && pageSize.get()>10 ?pageSize.get() : 10;
		int _pageNum = pageNum.isPresent() && pageNum.get()>0 ? pageNum.get(): 1;
		Pageable pageable = PageRequest.of(_pageNum-1, _pageSize);
		
		List<VehicleDto> vehicles = new ArrayList<VehicleDto>();
		
		if(companyId.isPresent()) {
			vehicles = this.vehicleRepository.listVehiclesByCompanyId(companyId.get(), pageable,vehicleStatusRentableId);
		}
		else {
			if(cityId.isPresent()) {
				
				vehicles = this.vehicleRepository.listVehicles(cityId.get(),pageable,vehicleStatusRentableId);
			}
			else {
				vehicles = this.vehicleRepository.listVehicles(pageable,vehicleStatusRentableId);
			}
		}
		
		
		return new SuccessDataResult<List<VehicleDto>>(Messages.vehiclesListed,vehicles);
	}

	@Override
	public Result rentACar(CarRentalDto carRentalDto) {
		String customerEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		
		Customer customer = this.customerService.getCustomerByEmail(customerEmail).getData();
		
		CarRentals rentalRequest = new CarRentals();
		rentalRequest.setRentalPeriod(carRentalDto.getRentalPeriod());
		rentalRequest.setRentalDate(carRentalDto.getRentalDate());
		rentalRequest.setCompany(new Company(carRentalDto.getCompanyId()));
		rentalRequest.setVehicle(new Vehicle(carRentalDto.getVehicleId()));
		rentalRequest.setCustomer(customer);
		rentalRequest.setApproved(false);		
		
		Result result = this.carRentalService.addCarRentalRequest(rentalRequest);
		log.info("A customer with "+ customerEmail + " has send a rental request.");
		return result;
	}

	@Override
	public DataResult<VehicleDto> listSingleVehicle(Optional<Integer> vehicleId) {
		if(!vehicleId.isPresent()) {
			return new ErrorDataResult<VehicleDto>(Messages.vehicleIdCannotBeNull);
		}
		VehicleDto vehicle = this.vehicleRepository.listSingleVehicle(vehicleId.get());
		if(vehicle == null) {
			return new ErrorDataResult<VehicleDto>(Messages.vehicleNotFound);
		}

		return new SuccessDataResult<VehicleDto>(vehicle);

	}

	

}

package com.webproje.arackiralama.Business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.webproje.arackiralama.Business.abstracts.AppUserService;
import com.webproje.arackiralama.Business.abstracts.CompanyManagerService;
import com.webproje.arackiralama.Business.abstracts.FuelTypeService;
import com.webproje.arackiralama.Business.abstracts.GearTypeService;
import com.webproje.arackiralama.Business.abstracts.VehicleService;
import com.webproje.arackiralama.Business.abstracts.VehicleStatusService;
import com.webproje.arackiralama.Business.constants.Messages;
import com.webproje.arackiralama.Core.entity.concretes.AppUser;
import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Core.utilities.result.concretes.ErrorResult;
import com.webproje.arackiralama.Core.utilities.result.concretes.SuccessDataResult;
import com.webproje.arackiralama.Core.utilities.result.concretes.SuccessResult;
import com.webproje.arackiralama.Entity.concretes.Company;
import com.webproje.arackiralama.Entity.concretes.CompanyManager;
import com.webproje.arackiralama.Entity.concretes.FuelType;
import com.webproje.arackiralama.Entity.concretes.GearType;
import com.webproje.arackiralama.Entity.concretes.Vehicle;
import com.webproje.arackiralama.Entity.concretes.VehicleStatus;
import com.webproje.arackiralama.Entity.dto.vehicleDtos.VehicleDto;
import com.webproje.arackiralama.Repository.CompanyManagerRepository;
import com.webproje.arackiralama.Repository.VehicleRepository;

@Service
public class VehicleManager implements VehicleService{

	private final VehicleRepository vehicleRepository;
	private final GearTypeService  gearTypeService;
	private final FuelTypeService fuelTypeService;
	private final VehicleStatusService vehicleStatusService;
	private final CompanyManagerService companyManagerService;
	
	@Autowired
	public VehicleManager(VehicleRepository vehicleRepository, GearTypeService gearTypeService,
				FuelTypeService fuelTypeService, VehicleStatusService vehicleStatusService,CompanyManagerService companyManagerService) {
			super();
			this.vehicleRepository = vehicleRepository;
			this.companyManagerService = companyManagerService;
			this.gearTypeService = gearTypeService;
			this.fuelTypeService = fuelTypeService;
			this.vehicleStatusService = vehicleStatusService;

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
		
		Company company =new Company();
		company.setId(this.companyManagerService.getCompanyIdByManagerEmail(managerEmail).getData());
		vehicle.setCompany(company);

		this.vehicleRepository.save(vehicle);

		return new SuccessResult(Messages.vehicleAdded);
	}


	@Override
	public Result updateVehicle(int vehicleId, VehicleDto vehicleDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataResult<List<VehicleDto>> listVehiclesForManager() {
		String managerEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		int companyId = this.companyManagerService.getCompanyIdByManagerEmail(managerEmail).getData();
		List<VehicleDto> vehicles = this.vehicleRepository.listVehiclesForManager(companyId);
		return new SuccessDataResult<List<VehicleDto>>(vehicles);
	}

	

}

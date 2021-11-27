package com.webproje.arackiralama.Business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webproje.arackiralama.Business.abstracts.FuelTypeService;
import com.webproje.arackiralama.Business.abstracts.GearTypeService;
import com.webproje.arackiralama.Business.abstracts.VehicleService;
import com.webproje.arackiralama.Business.abstracts.VehicleStatusService;
import com.webproje.arackiralama.Business.constants.Messages;
import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Core.utilities.result.concretes.ErrorResult;
import com.webproje.arackiralama.Core.utilities.result.concretes.SuccessResult;
import com.webproje.arackiralama.Entity.concretes.Company;
import com.webproje.arackiralama.Entity.concretes.FuelType;
import com.webproje.arackiralama.Entity.concretes.GearType;
import com.webproje.arackiralama.Entity.concretes.Vehicle;
import com.webproje.arackiralama.Entity.concretes.VehicleStatus;
import com.webproje.arackiralama.Entity.dto.VehicleDto;
import com.webproje.arackiralama.Repository.VehicleRepository;

@Service
public class VehicleManager implements VehicleService{

	private final VehicleRepository vehicleRepository;
	private final GearTypeService  gearTypeService;
	private final FuelTypeService fuelTypeService;
	private final VehicleStatusService vehicleStatusService;
	
	@Autowired
	public VehicleManager(VehicleRepository vehicleRepository, GearTypeService gearTypeService,
				FuelTypeService fuelTypeService, VehicleStatusService vehicleStatusService) {
			super();
			this.vehicleRepository = vehicleRepository;
			this.gearTypeService = gearTypeService;
			this.fuelTypeService = fuelTypeService;
			this.vehicleStatusService = vehicleStatusService;
	}
	
	
	@Override
	public Result addVehicle(VehicleDto vehicleDto) {

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
		company.setId(1);
		vehicle.setCompany(company);

		//TODO şirket bilgisi girilecek şimdilik 0 olarak verildi. Şirket bilgisi tokendan alınması gerekebilir.
		this.vehicleRepository.save(vehicle);

		return new SuccessResult(Messages.vehicleAdded);
	}

	

}

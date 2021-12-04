package com.webproje.arackiralama.Business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webproje.arackiralama.Business.abstracts.VehicleStatusService;
import com.webproje.arackiralama.Business.constants.Messages;
import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Core.utilities.result.concretes.ErrorDataResult;
import com.webproje.arackiralama.Core.utilities.result.concretes.SuccessDataResult;
import com.webproje.arackiralama.Core.utilities.result.concretes.SuccessResult;
import com.webproje.arackiralama.Entity.concretes.VehicleStatus;
import com.webproje.arackiralama.Repository.VehicleStatusRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VehicleStatusManager implements VehicleStatusService {

	private final VehicleStatusRepository vehicleStatusRepository;
	
	@Autowired
	public VehicleStatusManager(VehicleStatusRepository vehicleStatusRepository) {
		super();
		this.vehicleStatusRepository = vehicleStatusRepository;
	}
	
	@Override
	public DataResult<VehicleStatus> getByName(String name) {
		VehicleStatus vStatus = this.vehicleStatusRepository.getByName(name);
		if(vStatus != null) {
			return new SuccessDataResult<VehicleStatus>(vStatus);
		}
		
		else {
			return new ErrorDataResult<VehicleStatus>(Messages.vehicleStatusNotFound);
		}
	}

	@Override
	public Result addVehicleStatus(VehicleStatus vehicleStatus) {
		this.vehicleStatusRepository.save(vehicleStatus);
		log.info("A vehicle status has added to the service with "+ vehicleStatus.getName()+ " name.");
		return new SuccessResult(Messages.vehicleStatusAdded);
	}

	@Override
	public Result updateVehicleStatus(VehicleStatus vehicleStatus) {
		VehicleStatus status = this.vehicleStatusRepository.getById(vehicleStatus.getId());
		log.info("A vehicle type's name has been updated to"+vehicleStatus.getName()+" from "+ status.getName());
		status.setName(vehicleStatus.getName());
		this.vehicleStatusRepository.save(status);
		
		return new SuccessResult(Messages.vehicleStatusUpdated);
	}

	@Override
	public Result deleteVehicleStatus(int id) {
		this.vehicleStatusRepository.deleteById(id);
		log.info("A vehicle status has deleted from the service with "+ id+ " id.");
		return new SuccessResult(Messages.vehicleStatusDeleted);
	}

	@Override
	public DataResult<List<VehicleStatus>> listVehicleStatus() {
		log.info("Vehicle statuses have been listed.");
		return new SuccessDataResult<List<VehicleStatus>>(this.vehicleStatusRepository.findAll());
	}



	

}

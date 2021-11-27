package com.webproje.arackiralama.Business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webproje.arackiralama.Business.abstracts.VehicleStatusService;
import com.webproje.arackiralama.Business.constants.Messages;
import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.concretes.ErrorDataResult;
import com.webproje.arackiralama.Core.utilities.result.concretes.SuccessDataResult;
import com.webproje.arackiralama.Entity.concretes.VehicleStatus;
import com.webproje.arackiralama.Repository.VehicleStatusRepository;

@Service
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



	

}

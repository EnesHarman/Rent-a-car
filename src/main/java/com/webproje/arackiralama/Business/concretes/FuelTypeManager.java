package com.webproje.arackiralama.Business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webproje.arackiralama.Business.abstracts.FuelTypeService;
import com.webproje.arackiralama.Business.constants.Messages;
import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.concretes.ErrorDataResult;
import com.webproje.arackiralama.Core.utilities.result.concretes.SuccessDataResult;
import com.webproje.arackiralama.Entity.concretes.FuelType;
import com.webproje.arackiralama.Repository.FuelTypeRepository;

@Service
public class FuelTypeManager implements FuelTypeService{

	private final FuelTypeRepository fuelTypeRepository;
	
	@Autowired
	public FuelTypeManager(FuelTypeRepository fuelTypeRepository) {
		super();
		this.fuelTypeRepository = fuelTypeRepository;
	}
	
	@Override
	public DataResult<FuelType> getByName(String fuelType) {
		FuelType fType= this.fuelTypeRepository.getByName(fuelType);
		if(fType!=null) {
			return new SuccessDataResult<FuelType>(fType);
		}
		else {
			return new ErrorDataResult<FuelType>(Messages.fuelTypeNotFound);
		}
	}


	

}

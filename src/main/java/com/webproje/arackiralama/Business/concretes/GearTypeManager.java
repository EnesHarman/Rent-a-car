package com.webproje.arackiralama.Business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webproje.arackiralama.Business.abstracts.GearTypeService;
import com.webproje.arackiralama.Business.constants.Messages;
import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Core.utilities.result.concretes.ErrorDataResult;
import com.webproje.arackiralama.Core.utilities.result.concretes.SuccessDataResult;
import com.webproje.arackiralama.Core.utilities.result.concretes.SuccessResult;
import com.webproje.arackiralama.Entity.concretes.GearType;
import com.webproje.arackiralama.Repository.GearTypeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GearTypeManager implements GearTypeService{

	private final GearTypeRepository gearTypeRepository;
	
	@Autowired
	public GearTypeManager(GearTypeRepository gearTypeRepository) {
		super();
		this.gearTypeRepository = gearTypeRepository;
	}
	
	@Override
	public DataResult<GearType> getByName(String gearType) {
		GearType  gType = this.gearTypeRepository.getByName(gearType);
		if(gType != null) {
			log.info(gearType+ " has been added to the service as a gear type.");
			return new SuccessDataResult<GearType>(gType);
		}
		else {
			return new ErrorDataResult<GearType>(Messages.gearTypeNotFound);
		}
	}

	@Override
	public Result addGearType(GearType gearType) {
		this.gearTypeRepository.save(gearType);
		log.info("A gear type has added to the service with "+ gearType.getName()+ " name.");
		return new SuccessResult(Messages.gearTypeAdded);
	}

	@Override
	public Result deleteGearType(int gearTypeId) {
		this.gearTypeRepository.deleteById(gearTypeId);
		log.info("A gear type has deleted from the service with "+ gearTypeId+ " id.");
		return new SuccessResult(Messages.gearTypeDeleted);
	}

	@Override
	public Result updateGearType(GearType gearType) {
		GearType _gearType = this.gearTypeRepository.getById(gearType.getId());
		log.info("A gear type's name has been updated to"+gearType.getName()+" from "+ _gearType.getName());
		_gearType.setName(gearType.getName());
		this.gearTypeRepository.save(_gearType);
		return new SuccessResult(Messages.gearTypeUpdated);
	}

	@Override
	public DataResult<List<GearType>> listGearTypes() {
		log.info("Gear types have been listed.");
		return new SuccessDataResult<List<GearType>>(this.gearTypeRepository.findAll());
	}

	

}

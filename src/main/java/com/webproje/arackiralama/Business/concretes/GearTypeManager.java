package com.webproje.arackiralama.Business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webproje.arackiralama.Business.abstracts.GearTypeService;
import com.webproje.arackiralama.Business.constants.Messages;
import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.concretes.ErrorDataResult;
import com.webproje.arackiralama.Core.utilities.result.concretes.SuccessDataResult;
import com.webproje.arackiralama.Entity.concretes.GearType;
import com.webproje.arackiralama.Repository.GearTypeRepository;

@Service
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
			return new SuccessDataResult<GearType>(gType);
		}
		else {
			return new ErrorDataResult<GearType>(Messages.gearTypeNotFound);
		}
	}
	

}

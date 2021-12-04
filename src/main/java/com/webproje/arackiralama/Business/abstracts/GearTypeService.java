package com.webproje.arackiralama.Business.abstracts;

import java.util.List;

import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Entity.concretes.GearType;

public interface GearTypeService {

	DataResult<GearType> getByName(String gearType);

	Result addGearType(GearType gearType);

	Result deleteGearType(int gearTypeId);

	Result updateGearType(GearType gearType);

	DataResult<List<GearType>> listGearTypes();

}

package com.webproje.arackiralama.Business.abstracts;

import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Entity.concretes.GearType;

public interface GearTypeService {

	DataResult<GearType> getByName(String gearType);

}

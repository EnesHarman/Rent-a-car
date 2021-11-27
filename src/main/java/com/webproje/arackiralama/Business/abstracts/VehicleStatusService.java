package com.webproje.arackiralama.Business.abstracts;

import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Entity.concretes.VehicleStatus;

public interface VehicleStatusService {

	DataResult<VehicleStatus> getByName(String name);

}

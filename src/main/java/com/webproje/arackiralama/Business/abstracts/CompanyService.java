package com.webproje.arackiralama.Business.abstracts;

import java.util.List;

import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Entity.dto.companyDtos.CompanyAddDto;
import com.webproje.arackiralama.Entity.dto.vehicleDtos.VehicleDto;

public interface CompanyService {

	Result addCompany(CompanyAddDto companyAddDto);

}

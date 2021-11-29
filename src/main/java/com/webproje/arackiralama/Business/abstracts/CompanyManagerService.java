package com.webproje.arackiralama.Business.abstracts;


import java.util.List;

import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Entity.concretes.CompanyManager;
import com.webproje.arackiralama.Entity.dto.companyManagerDtos.CompanyManagerRegisterDto;
import com.webproje.arackiralama.Entity.dto.vehicleDtos.VehicleDto;

public interface CompanyManagerService {
	Result addCompanyManager(CompanyManagerRegisterDto companyManagerRegisterDto, int companyId);
	DataResult<Integer> getCompanyIdByManagerEmail(String email);

}

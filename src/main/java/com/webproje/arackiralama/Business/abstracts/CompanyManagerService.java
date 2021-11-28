package com.webproje.arackiralama.Business.abstracts;

import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Entity.dto.CompanyManagerRegisterDto;

public interface CompanyManagerService {
	Result addCompanyManager(CompanyManagerRegisterDto companyManagerRegisterDto, int companyId);
}

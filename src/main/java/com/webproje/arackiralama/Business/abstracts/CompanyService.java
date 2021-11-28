package com.webproje.arackiralama.Business.abstracts;

import com.webproje.arackiralama.Core.utilities.result.abstracts.Result;
import com.webproje.arackiralama.Entity.dto.CompanyAddDto;

public interface CompanyService {

	Result addCompany(CompanyAddDto companyAddDto);

}

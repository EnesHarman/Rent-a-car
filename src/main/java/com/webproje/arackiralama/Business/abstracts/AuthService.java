package com.webproje.arackiralama.Business.abstracts;

import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Entity.dto.auth.LoginDto;
import com.webproje.arackiralama.Entity.dto.auth.LoginSuccessDto;

public interface AuthService {
	DataResult<LoginSuccessDto> login(LoginDto loginDto);
}

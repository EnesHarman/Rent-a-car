package com.webproje.arackiralama.Business.concretes;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.webproje.arackiralama.Business.abstracts.AuthService;
import com.webproje.arackiralama.Core.security.constants.SecurityConstants;
import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.concretes.ErrorDataResult;
import com.webproje.arackiralama.Core.utilities.result.concretes.SuccessDataResult;
import com.webproje.arackiralama.Entity.dto.auth.LoginDto;
import com.webproje.arackiralama.Entity.dto.auth.LoginSuccessDto;

@Service
public class AuthManager implements AuthService{

	private final AuthenticationManager authenticationManager;
	
	@Autowired
	public AuthManager(AuthenticationManager authenticationManager) {
		super();
		this.authenticationManager = authenticationManager;
	}

	@Override
	public DataResult<LoginSuccessDto> login(LoginDto loginDto) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
		Authentication auth = this.authenticationManager.authenticate(authenticationToken);
		if(auth.isAuthenticated()) {
			User user =(User) auth.getPrincipal();
			Algorithm algorithm = Algorithm.HMAC256(SecurityConstants.secretKey.getBytes());
			String access_token = JWT.create()
					.withSubject(user.getUsername())
					.withExpiresAt(new Date(System.currentTimeMillis() +SecurityConstants.jwtExpireDate))
					.withClaim("role",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
					.sign(algorithm);
			LoginSuccessDto loginSuccessDto = new LoginSuccessDto();
			loginSuccessDto.setToken(access_token);
			loginSuccessDto.setRole(user.getAuthorities().toString());
			return new SuccessDataResult<LoginSuccessDto>(loginSuccessDto);
		}
		else {
			return new ErrorDataResult<LoginSuccessDto>();
		}
	}

}

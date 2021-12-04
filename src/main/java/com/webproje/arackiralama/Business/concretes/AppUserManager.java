package com.webproje.arackiralama.Business.concretes;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.webproje.arackiralama.Business.abstracts.AppUserService;
import com.webproje.arackiralama.Core.entity.concretes.AppUser;
import com.webproje.arackiralama.Core.utilities.result.abstracts.DataResult;
import com.webproje.arackiralama.Core.utilities.result.concretes.ErrorDataResult;
import com.webproje.arackiralama.Core.utilities.result.concretes.SuccessDataResult;
import com.webproje.arackiralama.Repository.AppUserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AppUserManager implements UserDetailsService, AppUserService{

	private final AppUserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	public AppUserManager(AppUserRepository userRepository,BCryptPasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user = this.userRepository.getByEmail(username);
		if(user == null) {
			log.error("User not found in database");
			throw new UsernameNotFoundException("Email not found in database");
		}
		else {
			log.info("User found in database {}"+ username);
		}
		
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		String role = user.getRole().getName();
		authorities.add(new SimpleGrantedAuthority(role));
		return new User(user.getEmail(), user.getPassword(), authorities);
	}

	@Override
	public AppUser saveUser(AppUser user) {
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		return this.userRepository.save(user);
	}

	@Override
	public DataResult<AppUser> getUserByEmail(String email) {
		 AppUser user = this.userRepository.getByEmail(email);
		 if(user == null) {
			 return new ErrorDataResult<AppUser>("There is a problem with your session. Please try again later.");
		 }
		 else {
			 return new SuccessDataResult<AppUser>(user);
		 }
	}

	
	
	

}

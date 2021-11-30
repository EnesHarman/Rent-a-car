package com.webproje.arackiralama.Core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.webproje.arackiralama.Core.security.filters.CustomAuthenticationFilter;
import com.webproje.arackiralama.Core.security.filters.CustomAuthorizationFilter;

import org.springframework.http.HttpMethod;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final UserDetailsService userDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
		customAuthenticationFilter.setFilterProcessesUrl("/api/login");
		
		http.csrf().disable(); // bakılacak
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/login/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/customer/register/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/systemmanager/register/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/company/add/**").hasAnyAuthority("ROLE_SYSTEM_MANAGER");
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/vehicle/add/**").hasAnyAuthority("ROLE_COMPANY_MANAGER");
		http.authorizeRequests().antMatchers(HttpMethod.PUT,"/api/vehicle/update/**").hasAnyAuthority("ROLE_COMPANY_MANAGER");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/api/vehicle/delete/**").hasAnyAuthority("ROLE_COMPANY_MANAGER");
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/vehicle/rent/**").hasAnyAuthority("ROLE_CUSTOMER");
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/vehicle/list/**").hasAnyAuthority("ROLE_COMPANY_MANAGER, ROLE_CUSTOMER");
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/companymanager/vehicles/list/**").hasAnyAuthority("ROLE_COMPANY_MANAGER");
		http.authorizeRequests()
        .antMatchers(
            "/v2/api-docs", 
            "/swagger-resources/**",  
            "/swagger-ui.html", 
            "/webjars/**" ,
              "/swagger.json")
        .permitAll();
		
		http.authorizeRequests().anyRequest().authenticated();
		http.addFilter(customAuthenticationFilter);
		http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
	} 
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception{
		return super.authenticationManagerBean();
	}
}

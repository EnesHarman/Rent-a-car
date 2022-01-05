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
import org.springframework.web.cors.CorsConfiguration;

import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


import com.webproje.arackiralama.Core.security.filters.CustomAuthorizationFilter;

import org.springframework.http.HttpMethod;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final UserDetailsService userDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	
	@Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
//		customAuthenticationFilter.setFilterProcessesUrl("/api/login");
		http.csrf().disable();
		http.cors();
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/auth/login/**").permitAll();
		
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/customer/register/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/customer/rentals/list/**").hasAnyAuthority("ROLE_CUSTOMER");
		
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/systemmanager/register/**").permitAll();
		
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/systemmanager/company/delete/**").hasAnyAuthority("ROLE_SYSTEM_MANAGER");
		
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/company/add/**").hasAnyAuthority("ROLE_SYSTEM_MANAGER");
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/company/list/**").permitAll();
		
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/fueltype/add/**").hasAnyAuthority("ROLE_SYSTEM_MANAGER");
		http.authorizeRequests().antMatchers(HttpMethod.PUT,"/api/fueltype/update/**").hasAnyAuthority("ROLE_SYSTEM_MANAGER");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/api/fueltype/delete/**").hasAnyAuthority("ROLE_SYSTEM_MANAGER");
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/fueltype/list/**").permitAll();
		
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/geartype/add/**").hasAnyAuthority("ROLE_SYSTEM_MANAGER");
		http.authorizeRequests().antMatchers(HttpMethod.PUT,"/api/geartype/update/**").hasAnyAuthority("ROLE_SYSTEM_MANAGER");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/api/geartype/delete/**").hasAnyAuthority("ROLE_SYSTEM_MANAGER");
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/geartype/list/**").permitAll();
		
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/role/add/**").hasAnyAuthority("ROLE_SYSTEM_MANAGER");
		http.authorizeRequests().antMatchers(HttpMethod.PUT,"/api/role/update/**").hasAnyAuthority("ROLE_SYSTEM_MANAGER");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/api/role/delete/**").hasAnyAuthority("ROLE_SYSTEM_MANAGER");
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/role/list/**").hasAnyAuthority("ROLE_SYSTEM_MANAGER");
		
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/vehiclestatus/list/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/vehiclestatus/add/**").hasAnyAuthority("ROLE_SYSTEM_MANAGER");
		http.authorizeRequests().antMatchers(HttpMethod.PUT,"/api/vehiclestatus/update/**").hasAnyAuthority("ROLE_SYSTEM_MANAGER");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/api/vehiclestatus/delete/**").hasAnyAuthority("ROLE_SYSTEM_MANAGER");
		
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/vehicle/add/**").hasAnyAuthority("ROLE_COMPANY_MANAGER");
		http.authorizeRequests().antMatchers(HttpMethod.PUT,"/api/vehicle/update/**").hasAnyAuthority("ROLE_COMPANY_MANAGER");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/api/vehicle/delete/**").hasAnyAuthority("ROLE_COMPANY_MANAGER");
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/vehicle/rent/**").hasAnyAuthority("ROLE_CUSTOMER");
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/vehicle/list/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/vehicle/details/**").permitAll();
		
		http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/api/companymanager/rentals/list/{requestId}/reject/**").hasAnyAuthority("ROLE_COMPANY_MANAGER");
		http.authorizeRequests().antMatchers(HttpMethod.PUT,"/api/companymanager/rentals/list/{requestId}/confirm/**").hasAnyAuthority("ROLE_COMPANY_MANAGER");
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/companymanager/vehicles/list/**").hasAnyAuthority("ROLE_COMPANY_MANAGER");
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/companymanager/rentals/list/**").hasAnyAuthority("ROLE_COMPANY_MANAGER");
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/companymanager/rentals/{rentalId}/return/**").hasAnyAuthority("ROLE_COMPANY_MANAGER");
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/companymanager/register/**").permitAll();
		
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/city/list/**").permitAll();
		
		http.authorizeRequests()
        .antMatchers(
            "/v2/api-docs", 
            "/swagger-resources/**",  
            "/swagger-ui.html", 
            "/webjars/**" ,
              "/swagger.json")
        .permitAll();
		
		http.authorizeRequests().anyRequest().authenticated();
		//http.addFilter(customAuthenticationFilter);
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

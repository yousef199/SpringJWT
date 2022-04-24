package com.example.demo.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.filters.AuthenticaitonFilter;
import com.example.demo.filters.AuthorizationFilter;

import lombok.RequiredArgsConstructor;

@Configuration @EnableWebSecurity @RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final UserDetailsService userDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
		}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		AuthenticaitonFilter authenticaitonFilter = new AuthenticaitonFilter(authenticationManagerBean());
		//change the default url 
		authenticaitonFilter.setFilterProcessesUrl("/api/login");
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers("/api/login/**" , "/api/token/refresh/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET , "/api/users/**").hasAnyAuthority("ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.POST , "/api/user/save/**").hasAnyAuthority("ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.POST , "/api/role/save/**").hasAnyAuthority("SUPER_ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.POST , "/api/role/addRoleToUser/**").hasAnyAuthority("SUPER_ADMIN");
		http.authorizeRequests().anyRequest().authenticated();
		http.addFilter(authenticaitonFilter);
		http.addFilterBefore(new AuthorizationFilter() , AuthenticaitonFilter.class);
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}

package com.example.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.UserAccount;
import com.example.security.UserAccountAuthenticationProvider;

@RestController
@EnableWebSecurity
public class authenticationController {
	@Autowired
	private UserAccountAuthenticationProvider authenticationProvider;
	
	@RequestMapping("/authenticate")
	public ResponseEntity<Principal> authenticateUser(Principal user){
		System.out.println("Inside.........");
		return new ResponseEntity<Principal>(user,HttpStatus.OK);
		
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}

	@Configuration
	@Order(1)
	public static class ApiWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			  http.csrf().disable();
			  http.headers().frameOptions().disable();
			  http
				.httpBasic().and()
				.logout().and()
				.authorizeRequests()
					.antMatchers("/**").permitAll()
					.anyRequest().authenticated()
					.and()
				.csrf()
					.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
			  
			  }
	}

}

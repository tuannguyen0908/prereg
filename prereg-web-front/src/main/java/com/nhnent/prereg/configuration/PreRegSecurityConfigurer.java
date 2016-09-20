package com.nhnent.prereg.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class PreRegSecurityConfigurer extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.anyRequest().permitAll()
				.and()
			.csrf().disable();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		String[] roles = new String[]{};
		auth.inMemoryAuthentication()
				.withUser("admin").password("12345678@Ab").roles(roles).and()
				.withUser("admin1").password("13579@Abcde").roles(roles).and()
				.withUser("admin2").password("gthXegey@Ab").roles(roles).and()
				.withUser("admin3").password("g2c:/Y|xQOb").roles(roles).and()
				.withUser("admin4").password("TNkO6u#'6U7").roles(roles).and()
				.withUser("admin5").password("xM}V45+xm~F").roles(roles);
	}

}

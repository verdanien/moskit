package com.mos.moskit.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		 auth
         .userDetailsService(userDetailsService)
         .passwordEncoder(passwordEncoder);
		
//		auth.inMemoryAuthentication().withUser("admin").password("admin").authorities(new GrantedAuthority() {
//			@Override
//			public String getAuthority() {
//				return "ADMIN";
//			}
//			
//		});
//		JdbcUserDetailsManagerConfigurer<AuthenticationManagerBuilder> jdbcAuth = auth.jdbcAuthentication();
//		jdbcAuth.dataSource(dataSource);
//		jdbcAuth.authoritiesByUsernameQuery("select login, password from user u where u.login = ?");
//		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		jdbcAuth.passwordEncoder(passwordEncoder);
//		jdbcAuth.and().jdbcAuthentication().authoritiesByUsernameQuery(query)
		
		
//		jdbcAuth.groupAuthoritiesByUsername(query)
//		auth.authenticationProvider(new AuthenticationProvider() {
//			
//			@Override
//			public boolean supports(Class<?> authentication) {
//				// TODO Auto-generated method stub
//				return false;
//			}
//			
//			@Override
//			public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//		});
//		auth.inMemoryAuthentication()
//		.withUser
//		super.configure(auth);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
	}
	
}

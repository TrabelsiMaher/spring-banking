package org.bnk.exls.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	@Bean
	public InMemoryUserDetailsManager detailsManager() {
		return new InMemoryUserDetailsManager(
				User.withUsername("user1").password(passwordEncoder.encode("1234")).roles("USER").build(),
				User.withUsername("admin").password(passwordEncoder.encode("1234")).roles("USER","Admin").build());
	}
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception  {
		
		
		return httpSecurity.sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.csrf(csrf->csrf.disable())
				.authorizeHttpRequests(ar->ar.anyRequest().authenticated())
				.httpBasic(t ->t.configure(httpSecurity))
				.build();
	}
	

}

package com.learner.caffeinated.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	@SuppressWarnings("removal")
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.csrf((csrf) -> csrf.disable())
				.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/admin/**", "/public/contact/close/**").hasRole("ADMIN")
				.requestMatchers(HttpMethod.POST, "/public/product", "/public/category").hasRole("ADMIN")
				.requestMatchers("/private/**").authenticated()
				.requestMatchers("/public/**").permitAll())
				.formLogin(Customizer.withDefaults()).httpBasic(Customizer.withDefaults());

		http.sessionManagement().invalidSessionUrl("/public/auth/session-expired");
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

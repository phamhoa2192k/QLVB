package edu.hust.document;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.hust.document.repository.RoleRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		ArrayList<String> roles = (ArrayList<String>) roleRepository.findAll().stream().map(role -> role.getName()).collect(Collectors.toList());
		String[] roleNames = roles.toArray(new String[roles.size()]);
		for(int i = 0; i < roles.size(); i++){
			roleNames[i] = roles.get(i);
		}
		http.authorizeRequests()
		 .antMatchers("/", "/login", "/dist/**", "/plugins/**").permitAll()
		 .antMatchers("/app/**").hasAnyAuthority(roleNames)
		 .antMatchers("/admin/**").hasAuthority("ADMIN")
		 .anyRequest().authenticated()
		.and()
	   .formLogin()
		 .loginPage("/login")
		 .loginProcessingUrl("/login")
		 .usernameParameter("username")
		 .passwordParameter("password")
		 .defaultSuccessUrl("/role")
		 .failureUrl("/login?success=failed")
		 .and()
		.logout()
		 .logoutUrl("/logout")
		 .logoutSuccessUrl("/")
		 .deleteCookies("JSESSIONID")
		 .permitAll()
		.and()
		.csrf().disable()
       .exceptionHandling()
         .accessDeniedPage("/error");
	}
}

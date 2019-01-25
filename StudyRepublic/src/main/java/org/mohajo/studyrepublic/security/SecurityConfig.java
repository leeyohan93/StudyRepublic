package org.mohajo.studyrepublic.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

import lombok.extern.java.Log;


@Log
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true) 
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetatilsServiceImpl memberservice;
	
	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("security config");
		
		http
		.csrf()
		.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
	
		http
		.authorizeRequests()
		.antMatchers("/admin/**","/signup/**").hasRole("A")
		.antMatchers("/tutor/**").hasRole("T")
		.antMatchers("/member/**").hasRole("N");
/*		.antMatchers("/").permitAll();*/
		// .antMatchers("/admin/**","/member/**").hasRole("A") 여기서 /member/** 는 동작하지 않음(/member 하위 접근권한 x. *바로 아랫줄 .antMatchers("/member/**").hasRole("N")가 있기 떼문
		// /member/** 에 대한 권한이 N인 사람의 권한으로 덮어쓰기됨. 이럴 경우 A권한인 사람은 N권한도 중복으로 가져야함. DB에서 등록.
		
				
		http
		.formLogin()
		.loginPage("/login").successHandler(new LoginSuccessHandler());
		
		http
		.exceptionHandling()
		.accessDeniedPage("/accessDenied");
		
		http.logout()
		.logoutUrl("/logout")
		.invalidateHttpSession(true)
		.deleteCookies("JSESSIONID","remember-me");
		
		http
		.rememberMe()
		.key("member")
		.userDetailsService(memberservice);
		
		http
		.rememberMe()
		.key("member")
		.rememberMeParameter("remember-me")
		.userDetailsService(memberservice)
		.tokenRepository(persistentTokenRepository())
		.tokenValiditySeconds(60*60*24);
	}
	
	@Bean		
	public SpringSecurityDialect springSecurityDialect() {
	    return new SpringSecurityDialect();
	}
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
	    JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
	    db.setDataSource(dataSource);
	    return db;
	    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(memberservice);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
    

	
	
}

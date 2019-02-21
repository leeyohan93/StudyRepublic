package org.mohajo.studyrepublic.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;


@Log
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true) 
@AllArgsConstructor
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
		.antMatchers("/login", "/member/signup","/member/insert","/member/checkid","/member/checknick","/member/findPassword","/member/successAuth","/member/modifyPassword","/tutor/profile").anonymous()
		.antMatchers("/kakaopay", "/", "/signup","/StudyPage/**","/index","/member/**").permitAll()
		.antMatchers("/tutor/signup","/tutor/insert","/pay","/board/**","/tutor/inquery","/tutor/file/**","/tutor/delete/**","/chat/studyChat").hasAnyRole("N","W","T","A")
		.antMatchers("/tutor").hasAnyRole("T","A")
		.antMatchers("/admin/**","/adminPage/**").hasRole("A");


						

		http
		.formLogin()
		.loginPage("/login")
		.successHandler(new LoginSuccessHandler())
		.defaultSuccessUrl("/index");
		
		
		http
		.exceptionHandling()
		.accessDeniedPage("/accessDenied");
		
		http.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.addLogoutHandler(new TaskImplementingLogoutHandler())
		.permitAll()
/*		.invalidateHttpSession(true)*/
		.logoutSuccessUrl("/index")
		.deleteCookies("JSESSIONID","remember-me")
		.invalidateHttpSession(true);

		
		http
		.rememberMe()
		.key("member")
		.rememberMeParameter("remember-me")
		.userDetailsService(memberservice)
		.tokenRepository(persistentTokenRepository())
		.tokenValiditySeconds(60*60*24);

		 
		 //스마트에디터 관련 설정
		 http.headers().frameOptions().sameOrigin();
		 
		
		http.addFilterAfter(new ExceptionHandlerFilter(), SecurityContextHolderAwareRequestFilter.class);
		

	}
	
/*	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**","/js/**");
	}*/
	
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
    
    
/*    private Filter ssoFilter() {
    	CompositeFilter filter = new CompositeFilter();
    	List <Filter> filters = new ArrayList<>();
    	filters.add(ssoFilter(facebook(), new Face))
    }*/

	
	
}


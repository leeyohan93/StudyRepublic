package org.mohajo.studyrepublic.security;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.mohajo.studyrepbulic.naver.NaverLoginBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@Log
@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableOAuth2Client
@EnableGlobalMethodSecurity(securedEnabled =true, jsr250Enabled = true, 	prePostEnabled = true)
	
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	/*@Autowired
	OAuth2ClientContext oauth2ClientContext;*/

	@Autowired
	UserDetatilsServiceImpl memberservice;

	@Autowired
	DataSource dataSource;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("security config");


		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

		http
				.authorizeRequests()
				.antMatchers("/login", "/member/signup", "/member/insert", "/member/checkid", "/member/checknick", "/member/findPassword", "/member/successAuth", "/member/modifyPassword").anonymous()
				.antMatchers("/kakaopay", "/", "/signup", "/StudyPage/**", "/index", "/member/**","/tutorFileUpload/**","/board/**" ,"/tutor/profile").permitAll()
				.antMatchers("/tutor/signup", "/tutor/insert", "/pay", "/board/**", "/tutor/inquery", "/tutor/file/**", "/tutor/delete/**", "/chat/studyChat", "/member/point/charge", "/member/modify/**", "/study/pleaseLogin/**", "/study/joinConfirm/**","/mypage/**","/activity/**","/message/**").hasAnyRole("N", "W", "T", "A")
				.antMatchers("/tutor").hasAnyRole("T", "A")
				.antMatchers("/admin/**", "/adminPage/**").hasRole("A");

		http.formLogin()
		.loginPage("/login")
		.successHandler(new LoginSuccessHandler())
		.failureHandler(new CustomAuthenticationFailureHandler())
		.defaultSuccessUrl("/index");

		http
		.exceptionHandling()
		.accessDeniedPage("/accessDenied");

		http.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				/* .invalidateHttpSession(true) */
				.logoutSuccessUrl("/index").deleteCookies("JSESSIONID", "remember-me")
				.invalidateHttpSession(true);

		http.
		rememberMe()
		.key("member")
		.rememberMeParameter("remember-me")
		.userDetailsService(memberservice)
				.tokenRepository(persistentTokenRepository())
				.tokenValiditySeconds(60 * 60 * 24);

		// 스마트에디터 관련 설정
		http.headers().frameOptions().sameOrigin();

	

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


	@Bean
	public CustomAuthenticationFailureHandler failurehandler() {
		CustomAuthenticationFailureHandler customLoginFailure = new CustomAuthenticationFailureHandler();
		return customLoginFailure;
	}


	


}

/**
 * 
 */
package org.mohajo.studyrepublic.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 이요한
 * @since 2019. 2. 20.
 * @version 0.0
 * -기능 설명1
 */
@Configuration
public class HttpInterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private HttpInterceptor httpInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	//excludePathPatterens 리팩토링필요
        registry.addInterceptor(httpInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/adminPage/**");
    }

}

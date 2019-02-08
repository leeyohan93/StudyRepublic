/**
 * 
 */
package org.mohajo.studyrepublic.fileupload;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author 윤성호
 * @since 2019.01.22
 * @version
 * -기능1 추가
 * -기능2 추가
 * -기능3 추가
 */

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"org.mohajo.studyrepublic.tutor.TutorController"})
public class WebConfig extends WebMvcConfigurerAdapter {

}

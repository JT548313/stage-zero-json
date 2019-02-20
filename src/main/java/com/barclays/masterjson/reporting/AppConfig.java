package com.barclays.masterjson.reporting;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.barclays.masterjson.web.filters.CorrelationHeaderFilter;

@Configuration
public class AppConfig {

	 @Bean
	 public FilterRegistrationBean <CorrelationHeaderFilter> filterRegistrationBean() {
	  FilterRegistrationBean <CorrelationHeaderFilter> registrationBean = new FilterRegistrationBean();
	  CorrelationHeaderFilter correlationHeaderFilter = new CorrelationHeaderFilter();

	  registrationBean.setFilter(correlationHeaderFilter);
	  registrationBean.addUrlPatterns("/api/v1/*");
	  registrationBean.setOrder(1); //set precedence
	  return registrationBean;
	 }
	
}

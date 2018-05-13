package com.neeson.example;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.neeson.example.filter.SpringFilter;
import com.neeson.example.properties.AliLiveProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.DispatcherType;

/**
 * @author neeson
 */
@SpringBootApplication
@MapperScan("com.neeson.example.mapper")
public class SpringbootExampleApplication {

	@Autowired
	private AliLiveProperties aliLiveProperties;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootExampleApplication.class, args);
	}

	@Bean
	public static FilterRegistrationBean filterRegistrationBean(){
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();

		filterRegistrationBean.setFilter(new SpringFilter());
		filterRegistrationBean.addServletNames("*");
		filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST,DispatcherType.FORWARD);

		return filterRegistrationBean;

	}

	@Bean
	public DefaultAcsClient initClient() {
		IClientProfile profile = DefaultProfile.getProfile(aliLiveProperties.getRegionId(), aliLiveProperties.getAccesskey(), aliLiveProperties.getAccessSecret());
		return new DefaultAcsClient(profile);
	}
}

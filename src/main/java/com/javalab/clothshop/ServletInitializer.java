package com.javalab.clothshop;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	// TODO: This should be stated explicitly and given a reason that an application should be deployed in old-school web
	//  container fashion since the default option still falls back to JAR courtesy of Docker containers and microservices
	//  architectures which often go together with Spring Boot projects, but is fine for your application.
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ClothShopApplication.class);
	}

}

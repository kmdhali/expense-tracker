package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@SpringBootApplication
@EnableCaching
public class PlaidApplication extends WebMvcConfigurerAdapter{

	@Override
	public void addViewControllers(ViewControllerRegistry   registry)
	{
		
		registry.addViewController("/home").setViewName("home");
		registry.addViewController("/home1").setViewName("home1");
		registry.addViewController("/ajaxCall").setViewName("ajaxCall");;
		registry.addViewController("/home4").setViewName("home4");
		registry.addViewController("/home5ajax").setViewName("home5ajax");
        registry.addViewController("/home5ajax2").setViewName("home5ajax2");
		registry.addViewController("/home5").setViewName("home5");
		registry.addViewController("/add-card-dropin").setViewName("add-card-dropin");
		registry.addViewController("/home6card").setViewName("home6card");
	}
    public static void main(String... args) {
        SpringApplication.run(PlaidApplication.class);
    }
}

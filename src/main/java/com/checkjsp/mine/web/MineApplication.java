package com.checkjsp.mine.web;

import com.checkjsp.mine.web.dao.DaoConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.checkjsp.mine.web")
public class MineApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MineApplication.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(MineApplication.class, args);
	}
}

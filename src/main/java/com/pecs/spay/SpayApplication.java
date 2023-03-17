package com.pecs.spay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication//(exclude = DataSourceAutoConfiguration.class)
public class SpayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpayApplication.class, args);
	}

}

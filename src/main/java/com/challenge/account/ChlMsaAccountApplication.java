package com.challenge.account;

import com.challenge.account.configuration.ApplicationPropertiesConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.challenge.account"})
@EnableConfigurationProperties({ApplicationPropertiesConfiguration.class})
public class ChlMsaAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChlMsaAccountApplication.class, args);
	}

}

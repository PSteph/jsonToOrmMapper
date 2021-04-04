package com.bopuniv.server;

import com.bopuniv.server.services.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author Piedjou Stephane
 */
@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
@ConfigurationPropertiesScan("com.bopuniv.server.website.util")
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

}

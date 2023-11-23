package com.example.e2ekernelengine;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class E2eKernelEngineApplication implements CommandLineRunner {

	private final DataLoader dataLoader;

	public E2eKernelEngineApplication(DataLoader dataLoader) {
		this.dataLoader = dataLoader;
	}

	public static void main(String[] args) {
		SpringApplication.run(E2eKernelEngineApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		dataLoader.loadInitialData();
	}
}

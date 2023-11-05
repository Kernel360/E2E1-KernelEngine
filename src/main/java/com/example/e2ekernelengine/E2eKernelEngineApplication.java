package com.example.e2ekernelengine;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class E2eKernelEngineApplication implements CommandLineRunner {

	// dataLoader를 사용한 이유:
	// application.properties에서 spring.jpa.hibernate.ddl-auto=create 설정할 때 테이블 생성과 동시에 주입하기 위함
	// 이미 테이블이 생성되고 데이터가 주입되었을 때는
	// application.properties에서 spring.jpa.hibernate.ddl-auto=validate로 바꾼 후,
	// dataLoader.loadInitialData();룰 주석처리
	private final DataLoader dataLoader;

	public E2eKernelEngineApplication(DataLoader dataLoader) {
		this.dataLoader = dataLoader;
	}

	public static void main(String[] args) {
		SpringApplication.run(E2eKernelEngineApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// spring.jpa.hibernate.ddl-auto=validate일 때 아래줄 주석처리
		// dataLoader.loadInitialData(); // 애플리케이션 시작 시점에 DataLoader의 loadInitialData() 메서드 호출
	}
}

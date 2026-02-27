package com.certificate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class CertificateSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CertificateSystemApplication.class, args);
	}

}

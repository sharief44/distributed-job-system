package com.example.pdfservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;


@EnableKafka
@SpringBootApplication
public class PdfserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PdfserviceApplication.class, args);
	}

}

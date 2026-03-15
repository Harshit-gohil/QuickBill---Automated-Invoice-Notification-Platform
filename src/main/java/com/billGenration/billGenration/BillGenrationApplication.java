package com.billGenration.billGenration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BillGenrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillGenrationApplication.class, args);
	}

}

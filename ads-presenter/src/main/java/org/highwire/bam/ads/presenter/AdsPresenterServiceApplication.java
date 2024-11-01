package org.highwire.bam.ads.presenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
 

@SpringBootApplication
@EnableCaching
   public class AdsPresenterServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdsPresenterServiceApplication.class, args);
	}

}

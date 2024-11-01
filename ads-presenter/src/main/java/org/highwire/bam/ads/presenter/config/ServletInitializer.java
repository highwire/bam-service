package org.highwire.bam.ads.presenter.config;

import org.highwire.bam.ads.presenter.AdsPresenterServiceApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AdsPresenterServiceApplication.class);
	}

}

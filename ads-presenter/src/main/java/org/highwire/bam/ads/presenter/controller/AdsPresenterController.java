package org.highwire.bam.ads.presenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;
import org.highwire.bam.ads.presenter.service.AdsPresenterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
public class AdsPresenterController {

	public final static Logger logger = LoggerFactory.getLogger(AdsPresenterController.class);

	@Autowired
	private AdsPresenterService adsPresenterService;

	@Autowired
	private ResourceLoader resourceLoader;

	@Value("${xsl.path}")
	private String xslPath;

	@GetMapping("/ads")
	public ResponseEntity<?> getAdsList(@RequestParam String publisherId, @RequestParam String jcode,
			@RequestParam String sectionPath) {
		logger.info("AdsPresenterController : getAdsList started");
		try {
			Resource xmlResource = resourceLoader.getResource("classpath:xml/Test.xml");
			String outputWriter = adsPresenterService.parseXSLT(xslPath, xmlResource, publisherId, jcode, sectionPath);

			logger.info("XSLT transformation completed successfully.");
			logger.info("AdsPresenterController : getAdsList ended");
			return ResponseEntity.ok(outputWriter);
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Error fetching or parsing file: " + e.getMessage());
		}

	}

	@GetMapping("/ads/clear-cache")
	public ResponseEntity<?> clearAdsCache(@RequestParam String publisherId, @RequestParam String jcode,@RequestParam String sectionPath,
			@RequestParam boolean masterReset) {
		logger.info("AdsPresenterController : clearAdsCache started");
		if (masterReset) {
			String result = adsPresenterService.evictAllEnteries();
			logger.info("AdsPresenterController : clearAdsCache ended");
			return ResponseEntity.ok(result);

		} else {
			String deletedKey = adsPresenterService.evictSpecificKey(publisherId, jcode,sectionPath);
			logger.info("AdsPresenterController : clearAdsCache ended");
			return ResponseEntity.ok(deletedKey);
		}

	}
}

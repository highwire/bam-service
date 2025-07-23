package org.highwire.bam.ads.log.processor.controller;

import org.highwire.bam.ads.log.processor.model.AdClickRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AdClickController {

    private static final Logger logger = LoggerFactory.getLogger(AdClickController.class);

    @PostMapping("/{publisherId}/{corpusCode}/adclicks")
    public String handleAdClick( 
    		@PathVariable String publisherId,
            @PathVariable String corpusCode, 
            @RequestBody AdClickRequest request) {
    	
        logger.info("Received ad click via JSON:");
        logger.info("Publisher ID : {}", publisherId);
        logger.info("Corpus Code  : {}", corpusCode);
        logger.info("Ad ID     : {}", request.getAd());
        logger.info("Clicked   : {}", request.isAdclick());
        logger.info("URL: {}", request.getUrl());

        // Add business logic here (e.g., DB save, file log)

        return "Ad click recorded successfully for publisher: " + publisherId;
    }
}
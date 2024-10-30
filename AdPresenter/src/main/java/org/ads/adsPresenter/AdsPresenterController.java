package org.ads.adsPresenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
 
import org.springframework.web.bind.annotation.GetMapping;
 
import org.springframework.web.bind.annotation.RestController;
  
 import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
 public class AdsPresenterController {

	public final static Logger logger = LoggerFactory.getLogger(AdsPresenterController.class);
	
	@Autowired
	private AdsPresenterService adsPresenterService;
	
	 @Autowired
	 private ResourceLoader resourceLoader;
 	 
 	  @Value("${xsl.path}")
	  private String xslPath;
	 
	@GetMapping("/readXMLData")
	public ResponseEntity<?> readXMLData(String publisherId,String jcode) {
		logger.info("AdsPresenterController : readXMLData started");
		try {
 			Resource xmlResource = resourceLoader.getResource("classpath:xml/Test.xml");
 			String sectionPath="/site/misc/about.xhtml";
			String outputWriter=	adsPresenterService.parseXSLT(xslPath,xmlResource,publisherId,jcode,sectionPath);
			
     	 
  			logger.info("XSLT transformation completed successfully.");
 			logger.info("AdsPresenterController : readXMLData ended");
            return ResponseEntity.ok(outputWriter);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching or parsing file: " + e.getMessage());
        }
		
 	}
	@GetMapping("/deleteKeyCache")
	public ResponseEntity<?> deleteCache(String publisherId,String jcode,Boolean masterReset) {
		logger.info("AdsPresenterController : deleteKeyCache started");
		if(masterReset==true) {
		String result=adsPresenterService.evictAllEnteries();
		logger.info("AdsPresenterController : deleteCache ended");
		return ResponseEntity.ok(result);
		
	}else {
		String deletedKey=adsPresenterService.evictSpecificKey(publisherId,jcode);
		logger.info("AdsPresenterController : deleteCache ended");
		return ResponseEntity.ok(deletedKey);
 	}
		
	}
}

package org.ads.adsPresenter;

import java.io.IOException;
 
import javax.xml.transform.TransformerConfigurationException;

import org.springframework.core.io.Resource;
 import org.springframework.stereotype.Service;
 
@Service
public interface AdsPresenterService {

	String parseXSLT(String xslPath, Resource xmlResource, String publisherId, String jcode, String sectionPath) throws IOException, TransformerConfigurationException, Exception;

	String evictSpecificKey(String publisherId, String jcode);

	String evictAllEnteries();

	
	
}

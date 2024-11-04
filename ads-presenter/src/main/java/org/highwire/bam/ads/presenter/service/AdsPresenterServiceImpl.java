package org.highwire.bam.ads.presenter.service;

 import java.io.PrintWriter;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.highwire.bam.ads.presenter.controller.AdsPresenterController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
 import org.springframework.stereotype.Service;

import net.sf.saxon.TransformerFactoryImpl;

@Service
public class AdsPresenterServiceImpl implements AdsPresenterService{
	public final static Logger logger = LoggerFactory.getLogger(AdsPresenterController.class);
 	 
	
	@Override
	@Cacheable(value = "xsltCache", key = "#publisherId + '-' + #jcode + '-' + #sectionPath")
	public String parseXSLT(String xslPath, Resource xmlResource,String publisherId, String jcode, String sectionPath) throws Exception {
		logger.info("AdsPresenterServiceImpl : parseXSLT started");
		try {
			
  		StreamSource xml = new StreamSource(xmlResource.getInputStream());
 		StreamSource xsltSource = new StreamSource(xslPath);
 		
		TransformerFactory factory = new TransformerFactoryImpl();
		Transformer transformer = factory.newTransformer(xsltSource); 
			
		  transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
          transformer.setOutputProperty(OutputKeys.METHOD, "xml");
          transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		  transformer.setParameter("publisherId", publisherId);
		  transformer.setParameter("jcode", jcode);
		  transformer.setParameter("sectionPath", sectionPath);
	      
 	            StringWriter outputWriter = new StringWriter();
                StreamResult result = new StreamResult(outputWriter);
                 transformer.transform(xml, result);
                 return outputWriter.toString();
		} catch (Exception e) {
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			e.printStackTrace(printWriter);
			printWriter.flush();
 			String stackTrace = writer.toString();
			logger.error("Failed to Process Result File " + stackTrace);
			System.out.println("Failed to Process Result File " + stackTrace);
			throw e;
	     }
		 
		
	}
	
	@Override
	@CacheEvict(value = "xsltCache", key = "#publisherId + '-' + #jcode + '-' + #sectionPath")
    public String evictSpecificKey(String publisherId, String jcode,String sectionPath) {
		return "Cache entry with key " + publisherId +" "+jcode + " "+ sectionPath +" has been evicted.";
    }
	
	@Override
	@CacheEvict(value = "xsltCache", allEntries = true)
	public String evictAllEnteries() {
	 
		return "All entries in the cache xsltCache have been cleared.";
	}
}

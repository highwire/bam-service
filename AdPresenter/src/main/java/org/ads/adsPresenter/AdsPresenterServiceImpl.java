package org.ads.adsPresenter;

 import java.io.PrintWriter;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

 import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
 import org.springframework.stereotype.Service;

import net.sf.saxon.TransformerFactoryImpl;

@Service
public class AdsPresenterServiceImpl implements AdsPresenterService{
	public final static Logger logger = LoggerFactory.getLogger(AdsPresenterController.class);

	@Override
	@Cacheable(value = "xsltCache", key = "#publisherId + '-' + #jcode")
	public String parseXSLT(Resource xslResource, Resource xmlResource,String publisherId, String jcode, String sectionPath) throws Exception {
		logger.info("AdsPresenterServiceImpl : parseXSLT started");
		try {
			
  		StreamSource xml = new StreamSource(xmlResource.getInputStream());
 		StreamSource xsltSource = new StreamSource(xslResource.getInputStream());
 		
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

}

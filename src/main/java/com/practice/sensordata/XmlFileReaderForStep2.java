package com.practice.sensordata;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Component;


@Component
public class XmlFileReaderForStep2 {
	private final static Logger logger= LogManager.getLogger(XmlFileReaderForStep2.class);
	
	@Value("classpath:HTE2NP.xml")
	private Resource xmlFilePath;
	@Autowired
	DailyAggregatedSensorData dailyAggregatedSensorData;
	
	@Bean
	public StaxEventItemReader<DailyAggregatedSensorData> getXmlReader(){
		
		logger.info("*************************xmlFileReaderForStep2 started*************************************");
		StaxEventItemReaderBuilder<DailyAggregatedSensorData> staxEventItemReaderBuilder= new StaxEventItemReaderBuilder<>();
		logger.info("xmlFileReaderForStep2 reader object is created");
		staxEventItemReaderBuilder.name("XmlFileReader for step2");
		logger.info("xmlFileReaderForStep2 reader name set");
		staxEventItemReaderBuilder.resource(xmlFilePath);
		logger.info("xmlFileReaderForStep2 reader resource set");
		staxEventItemReaderBuilder.unmarshaller((Unmarshaller) dailyAggregatedSensorData.getMarshller());
		logger.info("xmlFileReaderForStep2 reader unmarsheller  set");
		staxEventItemReaderBuilder.addFragmentRootElements(DailyAggregatedSensorData.ITEM_ROOT_ELEMENT_NAME);
		logger.info("xmlFileReaderForStep2 reader fragmentroot element set");
		logger.info("*************************xmlFileReaderForStep2 reader Completed*******************************");
		return staxEventItemReaderBuilder.build();
	}
}

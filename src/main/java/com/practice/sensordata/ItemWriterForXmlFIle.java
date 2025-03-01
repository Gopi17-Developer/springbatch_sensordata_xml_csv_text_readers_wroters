package com.practice.sensordata;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.batch.item.xml.builder.StaxEventItemWriterBuilder;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Component;

@Component
public class ItemWriterForXmlFIle{

	private final ResourceLoader resourceLoader;
	
	public ItemWriterForXmlFIle(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

	private final static Logger logger = LogManager.getLogger(ItemProcessorForTextFile.class);

	public StaxEventItemWriter<DailyAggregatedSensorData> getItemWriterBuilder(){
		// Load the file as a WritableResource
        WritableResource outputXmlResource = (WritableResource) resourceLoader.getResource("file:HTE2NP.xml");
		logger.info("StaxEventItemWriterBuilder for ItemWriterForXmlFIle  Started: ");
		StaxEventItemWriterBuilder<DailyAggregatedSensorData> staxEventItemWriterBuilder = new StaxEventItemWriterBuilder<>();
		staxEventItemWriterBuilder.name("Xml writer For DailyAggregatedSensorData");
		logger.info("StaxEventItemWriterBuilder for ItemWriterForXmlFIle name set done ");
		staxEventItemWriterBuilder.marshaller(DailyAggregatedSensorData.getMarshller());
		logger.info("StaxEventItemWriterBuilder for ItemWriterForXmlFIle marsheller set done ");
		staxEventItemWriterBuilder.resource(outputXmlResource);
		logger.info("StaxEventItemWriterBuilder for ItemWriterForXmlFIle: Resource Set");
		staxEventItemWriterBuilder.rootTagName("data");
		logger.info("StaxEventItemWriterBuilder for ItemWriterForXmlFIle: Root Tag Name: Data");
		staxEventItemWriterBuilder.overwriteOutput(true);
		logger.info("StaxEventItemWriterBuilder for ItemWriterForXmlFIle: overwrite set to true");
		return staxEventItemWriterBuilder.build();
	}
}

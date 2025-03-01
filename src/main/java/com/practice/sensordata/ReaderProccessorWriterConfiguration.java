package com.practice.sensordata;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;


@Component
@Configuration
public class ReaderProccessorWriterConfiguration {
	
	private final Logger logger = LogManager.getLogger(ReaderProccessorWriterConfiguration.class);
	
    @Value("classpath:HTE2NP.txt")
    Resource rawDailyInputResource;
    
    @Bean
    public FlatFileItemReader<DailySensorData> textFileReader(){
    	logger.info("FLATFILE READER STARTED");
    	FlatFileItemReaderBuilder<DailySensorData> flatFileItemReaderBuilder = new FlatFileItemReaderBuilder<>();
    	flatFileItemReaderBuilder.name("aggregate-sensor-textfile-reader");
    	logger.info("FLATFILE READER NAME ASSIGNED: ");
    	flatFileItemReaderBuilder.resource(rawDailyInputResource);
    	logger.info("FLATFILE READER  RESOYRCE ALLOCATED SUCCESSFULLY");
    	flatFileItemReaderBuilder.lineMapper(new SensorDataTextLineMapper());
    	logger.info("FLATFILE READER LINE MAPPER ALLOCATED SUCCESSFULLY");
    	return flatFileItemReaderBuilder.build();
    }

	
}

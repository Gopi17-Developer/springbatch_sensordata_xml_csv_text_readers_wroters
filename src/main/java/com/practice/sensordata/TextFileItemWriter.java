package com.practice.sensordata;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class TextFileItemWriter {
	
	private static final Logger LOGGER= LogManager.getLogger(TextFileItemWriter.class);
	@Autowired
	ResourceLoader resourceLoader;
	
	@Bean
	public FlatFileItemWriter<DataAnomaly> flatFileItemReader(){
		
		FileSystemResource resource = new FileSystemResource("HTE2NP-anomalies.csv");
		FlatFileItemWriterBuilder<DataAnomaly> flatFileItemWriterBuilder= new FlatFileItemWriterBuilder<>();
		// Configure line aggregator
		DelimitedLineAggregator<DataAnomaly> lineAggregator = new DelimitedLineAggregator<>();
		lineAggregator.setDelimiter(",");
		BeanWrapperFieldExtractor<DataAnomaly> beanWrapperFieldExtractor = new BeanWrapperFieldExtractor<>();
		beanWrapperFieldExtractor.setNames(new String[] {"date","type","value"});
		lineAggregator.setFieldExtractor(beanWrapperFieldExtractor);
		
		LOGGER.info("**********TextFileItemWriter started for step2*******************");
		LOGGER.info("TextFileItemWriter Object created succesfully");
		flatFileItemWriterBuilder.name("Writer for step2");

		LOGGER.info("TextFileItemWriter Object writer name set");
		flatFileItemWriterBuilder.resource(resource);
		LOGGER.info("TextFileItemWriter Object resource set");
		flatFileItemWriterBuilder.lineAggregator(lineAggregator);
		LOGGER.info("TextFileItemWriter Object line aggregator set");
		LOGGER.info("***************Writer for Step2 Completed**************************");
		return flatFileItemWriterBuilder.build();
	}
}

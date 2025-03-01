package com.practice.sensordata;



import javax.batch.api.chunk.ItemReader;
import javax.batch.api.chunk.ItemWriter;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.Job;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@PropertySource("classpath:application.properties")
public class TemperatureSensorRootConfiguration extends DefaultBatchConfigurer{
	//defining am logger object
	private final Logger logger= LogManager.getLogger(TemperatureSensorRootConfiguration.class);
    @Autowired
    DataSource dataSource;
    
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    ReaderProccessorWriterConfiguration readerProccessorWriterConfiguration;
 
    @Autowired
    ItemWriterForXmlFIle itemWriterForXmlFIle2;
    @Autowired
    ItemProcessorForTextFile itemProcessorForTextFile;
  
    @Autowired
    @Qualifier("aggregateSensorStep")
    private Step aggregateSensorStep;
   
    //step-2
    @Autowired
    private  XmlFileReaderForStep2 xmlFileReaderForStep2;
    @Autowired
    private Step2ProcessorForAnamoly step2ProcessorForAnamoly;
    @Autowired
    private TextFileItemWriter textFileItemWriter;

    //defining a platform transaction Manager bean for setting up the datasource
    @Bean
    public PlatformTransactionManager platformTransactionManager() {
    	
       DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
       
       dataSourceTransactionManager.setDataSource(dataSource);
       logger.info("Transaction bean created succefully and assinged Datasource");
    	return dataSourceTransactionManager;
    }
    //creating a step for aggregateSensorStep
    @Bean
    @Qualifier("aggregateSensorStep")
    public Step aggregateSensorStep(JobRepository jobRepository,PlatformTransactionManager platformTransactionManager) {
    	
    	logger.info("Defining aggregateSensorStep...");
    	return new StepBuilderFactory(jobRepository, platformTransactionManager)
    			.get("aggregateSensorStep")
    			.<DailySensorData, DailyAggregatedSensorData>chunk(1)
    			.reader(readerProccessorWriterConfiguration.textFileReader())
    			.processor(itemProcessorForTextFile)
    			.writer(itemWriterForXmlFIle2.getItemWriterBuilder())
    			
    			.build();
    }
    @Bean
    @Qualifier("anamolyStep")
    public Step anamaolyStep() {
    	
    	logger.info("Defining anamolyStep...");
    	return new StepBuilderFactory(getJobRepository(), getTransactionManager())
    			.get("anamoly step")
    			.<DailyAggregatedSensorData,DataAnomaly>chunk(1)
    			.reader(xmlFileReaderForStep2.getXmlReader())
    			.processor(step2ProcessorForAnamoly)
    			.writer(textFileItemWriter.flatFileItemReader())
    			.build();
    }
    @Bean
    public Job aggregateSensorJob() {
        logger.info("Creating aggregateSensorJob...");
        return jobBuilderFactory.get("hai")
        		.incrementer(new RunIdIncrementer())// Allows re-running the job
                .start(aggregateSensorStep)
                .next(anamaolyStep())
                .build();
    }
	
    
    
  
    

	/*
	 * @Bean public DataSource dataSource() {
	 * 
	 * DriverManagerDataSource dataSource = new DriverManagerDataSource();
	 * System.out.println(password+driverClassName+username+url);
	 * dataSource.setPassword(password);
	 * dataSource.setDriverClassName(driverClassName);
	 * dataSource.setUsername(username); dataSource.setUrl(url);
	 * 
	 * return dataSource;
	 * 
	 * }
	 */
	/*
	 * @Bean public DataSourceInitializer dataSourceInitializer( DataSource
	 * dataSource,
	 * 
	 * @Value("${spring.batch.initialize-schema}") String initializationMode) {
	 * 
	 * DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
	 * dataSourceInitializer.setDataSource(dataSource);
	 * 
	 * ResourceDatabasePopulator resourceDatabasePopulator = new
	 * ResourceDatabasePopulator();
	 * 
	 * if ("always".equalsIgnoreCase(initializationMode)) { // Corrected path (use a
	 * custom schema file if needed) resourceDatabasePopulator.addScript(new
	 * ClassPathResource("org/springframework/batch/core/schema-mysql.sql"));
	 * 
	 * // Optional: Uncomment this if you want to clean up the schema before
	 * reinitialization //
	 * resourceDatabasePopulator.setCleanOnValidationError(true);
	 * 
	 * dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator); }
	 * 
	 * return dataSourceInitializer; }
	 */}
	



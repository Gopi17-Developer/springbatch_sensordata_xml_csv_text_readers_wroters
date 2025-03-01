package com.practice.sensordata;

import java.util.List;
import java.util.OptionalDouble;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;


@Component
public class ItemProcessorForTextFile implements ItemProcessor<DailySensorData, DailyAggregatedSensorData>{
	
	private final Logger logger = LogManager.getLogger(ItemProcessorForTextFile.class);
	@Override
	public DailyAggregatedSensorData process(DailySensorData item) throws Exception {
		
		// TODO Auto-generated method stub
		//set the date
		String date = item.getDate();
	
		List<Double> measurments = item.getMeasurments();
		logger.info("ItemProcessorForTextFile  Started");
		double avg = measurments.stream().mapToDouble(Double::doubleValue).sum()/measurments.size();
		logger.info("ItemProcessorForTextFile avg calulated");
		OptionalDouble min = measurments.stream().mapToDouble(Double::doubleValue).min();
		logger.info("ItemProcessorForTextFile min calulated");
		OptionalDouble max = measurments.stream().mapToDouble(Double::doubleValue).max();
		logger.info("ItemProcessorForTextFile max calulated");
		
		DailyAggregatedSensorData sensorData= new DailyAggregatedSensorData();
		
		sensorData.setAvg(avg);
		logger.info("ItemProcessorForTextFile avg calulated and set");
		sensorData.setMin(min.getAsDouble());
		logger.info("ItemProcessorForTextFile min calulated and set");

		sensorData.setMax(max.getAsDouble());
		
		logger.info("ItemProcessorForTextFile max calulated and set");
		
		sensorData.setDate(date);
		
		logger.info("ItemProcessorForTextFile date set");

		return sensorData;
	}

}

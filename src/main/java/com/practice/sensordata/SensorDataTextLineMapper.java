package com.practice.sensordata;

import java.util.ArrayList;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class SensorDataTextLineMapper implements LineMapper<DailySensorData> {
	
	private final Logger logger= LogManager.getLogger(SensorDataTextLineMapper.class);
	@Override
	public DailySensorData mapLine(String line, int lineNumber) throws Exception {
		// TODO Auto-generated method stub
		//you will get line as 
		//01-06-2015:76.53,76.81,76.00,75.30,74.91,74.05,73.93,74.02,74.87
		logger.info("LINE MAPPER STATRTED FOR MAP THE LINE");

		String[] splittedLineData = line.split(":");
		logger.info("SPLITTED WITH : DONE");
		DailySensorData dailySensorData = new DailySensorData();
		dailySensorData.setDate(splittedLineData[0]);
		logger.info("DATE ASSIGNED");
		String[] splittedWithComma = splittedLineData[1].split(",");
		logger.info("SPLITTED WITH , DONE");

		ArrayList<Double> commaSeparatedValue=new ArrayList<Double>();
		for(String commaValueString:splittedWithComma) {
			commaSeparatedValue.add(Double.parseDouble(commaValueString));
		}
		
		dailySensorData.setMeasurments(commaSeparatedValue);
		logger.info("LIST OF VALUES ARE ASSGINED");
		return dailySensorData;
	}

}

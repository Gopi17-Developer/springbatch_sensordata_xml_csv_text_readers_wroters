package com.practice.sensordata;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class Step2ProcessorForAnamoly implements ItemProcessor<DailyAggregatedSensorData, DataAnomaly>{
	 // Threshold for low / high ratio to be considered anomaly vs normal
    private static final double THRESHOLD = 0.9;

	private final static Logger logger=LogManager.getLogger(Step2ProcessorForAnamoly.class);
	@Override
	public DataAnomaly process(DailyAggregatedSensorData item) throws Exception {
		// TODO Auto-generated method stub
		logger.info("***********************Step2ProcessorForAnamoly started***************************");
		DataAnomaly dataAnomaly = new DataAnomaly();
		logger.info("Data anomaly object is created");
		if ((item.getMin() / item.getAvg()) < THRESHOLD) {
			logger.info("WITH IN THRESH HOLD LIMIT ");
            return new DataAnomaly(item.getDate(), AnomalyType.MINIMUM, item.getMin());
        } else if ((item.getAvg() / item.getMax()) < THRESHOLD) {
        	logger.info("WITH IN THRESH HOLD LIMIT ");
            return new DataAnomaly(item.getDate(), AnomalyType.MAXIMUM, item.getMax());
        } else {
        	logger.info("OUTSIDE  THRESH HOLD LIMIT ");
            // Convention is to return null to filter item out and not pass it to the writer
            return null;
        }
		
	}

}

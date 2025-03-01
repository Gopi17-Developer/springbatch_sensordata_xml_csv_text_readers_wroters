package com.practice.sensordata;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class DataAnomaly {
	// Represented as 'MM-dd-yyyy'
    private String date;
    private AnomalyType type;
    private double value;
}

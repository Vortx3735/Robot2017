package org.usfirst.frc.team3735.robot.util.calc;

import java.util.Arrays;

public class DDxLimiter {
	public double value;
	public double dyValue;
	private Range range;
	
	private DDxLimiter derivative;
	
	public DDxLimiter(double val, Range... ranges){
		this.value = val;
		range = ranges[0];
		if(ranges.length > 1){
			Range[] newRanges = new Range[ranges.length-1];
			for(int i = 0; i < newRanges.length; i++){
				newRanges[i] = ranges[i+1];
			}
			derivative = new DDxLimiter(0, newRanges);
		}
	}
	
	public double feed(double newVal){
		
		if(derivative != null){
			dyValue = derivative.feed(newVal - value);
		}else{
			dyValue = newVal - value;
		}
			
		value += range.limit(dyValue);
		
		return value;
	}
	
	public void reset(){
		reset(0);
	}
	
	public void reset(double num) {
		value = num;
		if(derivative != null){
			derivative.reset();
		}
	}
}

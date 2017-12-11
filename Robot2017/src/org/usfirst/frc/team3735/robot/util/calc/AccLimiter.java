package org.usfirst.frc.team3735.robot.util.calc;

public class AccLimiter {
	public double value;
	private Range range;
	
	public AccLimiter(double val, Range lim){
		this.value = val;
		this.range = lim;
	}
	
	public double feed(double newVal){
		double dy = newVal - value;
		value+= range.limit(dy);
		return value;
	}
}

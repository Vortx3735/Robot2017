package org.usfirst.frc.team3735.robot.util.calc;

public class JerkLimiter {
	public double value;
	public double dyValue;
	private Range range;
	private Range accRange;
	
	public JerkLimiter(double val, Range lim, Range acclim){
		this.value = val;
		this.range = lim;
		this.accRange = acclim;
	}
	
	public double feed(double newVal){
		double dy = newVal - value;
		
			double ddy = dy - dyValue;
			dyValue += accRange.limit(ddy);
			
		value += range.limit(dyValue);
		
		return value;
	}
	
	public void reset(){
		value = 0;
		dyValue = 0;
	}
	
	public void reset(double val){
		value = val;
		dyValue = 0;
	}
}

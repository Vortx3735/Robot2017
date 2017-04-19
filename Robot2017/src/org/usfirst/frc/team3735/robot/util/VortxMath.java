package org.usfirst.frc.team3735.robot.util;

public class VortxMath {
	
	public static boolean isWithinThreshold(double value, double target, double threshold){
		return Math.abs(target - value) < threshold;
	}

	public static double limit(double value, double lower, double higher) {
		if(lower > higher){
			System.out.println("Vortx Math Limit Error");
		}
		if(value < lower){
			value = lower;
		}else if(value > higher){
			value = higher;
		}
		return value;
	}
	
	public static double continuousLimit(double value, double minValue, double maxValue){
		if(value < minValue){
			return value + (maxValue - minValue);
		}else if(value > maxValue){
			return value - (maxValue - minValue);
		}else{
			return value;
		}
	}
	
	public static double curve(double value, double exponent){
		return value * Math.pow(Math.abs(value), exponent - 1);
		//return Math.signum(value) * Math.pow(Math.abs(value), exponent);
	}
	
	public static double curveAround(double value, double exponent, double stagnant){
		return curve(value / stagnant, exponent) * stagnant;
	}
}

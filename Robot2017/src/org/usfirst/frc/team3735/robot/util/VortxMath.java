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
}

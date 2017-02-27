package org.usfirst.frc.team3735.robot.util;

public class VortxMath {
	
	public static boolean isWithinThreshold(double value, double target, double threshold){
		return Math.abs(target - value) < threshold;
	}
}

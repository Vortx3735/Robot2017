package org.usfirst.frc.team3735.robot.util.calc;

public class VortxMath {
	
	/**
	 * Checks if a value is by a target
	 * 
	 * @param value		the value to check
	 * @param target	the target that the value should be near
	 * @param threshold	the maximum distance from the target the value can be
	 */
	public static boolean isWithinThreshold(double value, double target, double threshold){
		return Math.abs(target - value) < threshold;
	}

	/**
	 * limits a value to a certain range
	 * 
	 * @param value		the value to limit with the bounds
	 * @param lower		the lower bound
	 * @param upper		the upper bound 
	 */
	public static double limit(double value, double lower, double upper) {
		if(lower > upper){
			System.out.println("Vortx Math Limit Error");
		}
		if(value < lower){
			value = lower;
		}else if(value > upper){
			value = upper;
		}
		return value;
	}
	
	/**
	 * Modifies  a number to meet a certain range. For example, a motor
	 * that needs angle values in the range (0,360)
	 * @param value		the value to be modified
	 * @param minValue	the minimum value for the continuous range
	 * @param maxValue	the maximum value for the continuous range. Equivalent to the minValue
	 * @return			the same value but in the limited range
	 */
	public static double continuousLimit(double value, double minValue, double maxValue){
		while(value < minValue){
			value += (maxValue - minValue);
		}
		while(value > maxValue){
			value -= (maxValue - minValue);
		}
		return value;
	}
	
	public static double navLimit(double value) {
		return continuousLimit(value, -180, 180);
	}
	
	/**
	 * Curves a value with fixed points at -1 and 1
	 * Higher exponents reduce the curve below the y = x line
	 * Lower exponents raise the curve
	 * 
	 * @param value		the value to be curved
	 * @param exponent	the exponent to raise it to
	 * @return			the modified value
	 */
	public static double curve(double value, double exponent){
		return value * Math.pow(Math.abs(value), exponent - 1);
		//return Math.signum(value) * Math.pow(Math.abs(value), exponent);
	}
	
	/**
	 * Does the same as {@link #curve(double, double)}, except that stagnant is 
	 * the stationary value.
	 * 
	 * @param value		the value to be modified
	 * @param exponent	the exponent to raise it to
	 * @param stagnant	the the stationary value. Can be positive or negative
	 * @return			the curved value
	 */
	public static double curveAround(double value, double exponent, double stagnant){
		return curve(value / stagnant, exponent) * stagnant;
	}
}

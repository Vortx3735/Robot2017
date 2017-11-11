package org.usfirst.frc.team3735.robot.util.settings;

//@FunctionalInterface
public class Func {
	/**
	 * Literally just a freaking class for returning a value
	 * Like java seriously can't treat a method as a variable and can't keep up with python
	 * They don't include built-ins and shorthand notation for easy computing
	 * And their lambda functions are so hard to understand
	 * 
	 * Anyway, to use, just type
	 * 
	 * new Func(){() -> {
	 * 		return yourStuff;
	 * }
	 * 
	 * ....
	 * Okay actually I just tried that and it didn't even work
	 * You literally just have to write out the Anonymous class :/
	 * Yo what is up with this language. And how does it allow me to use the annotation
	 * This interface is clearly not very functional
	 */

	public double getValue(){
		return 0;
	};
	
	public static Func getFunc(double val){
		return new Func(){
			@Override
			public double getValue(){
				return val;
			}
		};
	}
}

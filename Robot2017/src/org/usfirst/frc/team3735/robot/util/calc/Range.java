package org.usfirst.frc.team3735.robot.util.calc;

import org.usfirst.frc.team3735.robot.util.settings.Func;

public class Range {
	
	Func lower, upper;
	
	public Range(double lower, double upper){
		this.lower = new Func(){
			public double getValue(){
				return lower;
			}
		};
		
		this.upper = new Func(){
			public double getValue(){
				return upper;
			}
		};
	}
	public Range(Func lower, Func upper){
		this.lower = lower;
		this.upper = upper;
	}
	
	public Range(boolean flag, double upper){
		this.lower = Func.getFunc(Double.NEGATIVE_INFINITY);
		this.upper = Func.getFunc(upper);
	}
	
	public Range(double lower, boolean flag){
		this.lower = Func.getFunc(lower);
		this.upper = Func.getFunc(Double.POSITIVE_INFINITY);
	}
	
	public Range(double lim){
		this(-lim, lim);
	}
	
	public Range(Func lim){
		lower = new Func(){
			@Override
			public double getValue(){
				return lim.getValue() * -1;
			}
		};
		upper = lim;
	}
	
	public double limit(double num){
		if(num < lower.getValue()){
			return lower.getValue();
		}
		if(num > upper.getValue()){
			return upper.getValue();
		}
		return num;
	}
}

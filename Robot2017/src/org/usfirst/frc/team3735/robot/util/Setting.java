package org.usfirst.frc.team3735.robot.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Setting {
	
	private String name;
	private double value;
	
	private boolean isReceiving;
	private boolean isVisible;
	
	public Setting(String name, double defaultValue){
		this.name = name;
		this.value = defaultValue;
		
		SmartDashboard.putNumber(name, defaultValue);
		isVisible = true;
		isReceiving = true;
	}

	
	/*
	 * @param flag	if true, it displays to the SmartDashboard but doesn't take input from it
	 * 				if false, the setting does not appear on the SmartDasboard
	 * 
	 */
	public Setting(String name, double defaultValue, boolean flag){
		this.name = name;
		this.value = defaultValue;
		
		if(flag){
			SmartDashboard.putNumber(name, defaultValue);
			isVisible = true;
		}else{
			isVisible = false;
		}
		isReceiving = false;
	}

	
	public double getValue(){
		if(isReceiving){
			value = SmartDashboard.getNumber(name, value);
		}
		return value;
	}
	
	public void setValue(double value){
		this.value = value;
		if(isVisible){
			SmartDashboard.putNumber(name, this.value);
		}
	}
	
}


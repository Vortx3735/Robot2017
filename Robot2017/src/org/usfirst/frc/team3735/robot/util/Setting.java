package org.usfirst.frc.team3735.robot.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Setting {
	
	private String name;
	private double value;
	
	public Setting(String name, double defaultValue){
		this.name = name;
		this.value = defaultValue;
		SmartDashboard.putNumber(name, defaultValue);
	}
	
	public double getValue(){
		value = SmartDashboard.getNumber(name, value);
		return value;
	}
	
	
	
}

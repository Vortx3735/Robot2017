package org.usfirst.frc.team3735.robot.util.settings;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class StringSetting {
	
	private String name;
	private String value;
	
	private boolean isReceiving;
	private boolean isVisible;
	
	public StringSetting(String name, String defaultValue){
		this.name = name;
		this.value = defaultValue;
		
		SmartDashboard.putString(name, defaultValue);
		isVisible = true;
		isReceiving = true;
	}

	
	/*
	 * @param flag	if true, it displays to the SmartDashboard but doesn't read take input from it
	 * 				if false, the setting does not appear on the SmartDasboard
	 * 
	 */
	public StringSetting(String name, String defaultValue, boolean flag){
		this.name = name;
		this.value = defaultValue;
		
		if(flag){
			SmartDashboard.putString(name, defaultValue);
			isVisible = true;
		}else{
			isVisible = false;
		}
		isReceiving = false;
	}

	
	public String getValue(){
		if(isReceiving){
			value = SmartDashboard.getString(name, value);
		}
		return value;
	}
	
	public void setValue(String value){
		this.value = value;
		if(isVisible){
			SmartDashboard.putString(name, this.value);
		}
	}
	
}



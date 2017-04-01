package org.usfirst.frc.team3735.robot.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Setting {
	
	private String name;
	private double numberValue;
	private String stringValue;
	private Type type;
	
	private enum Type{
		Number,String;
	}
	
	public Setting(String name, double defaultValue){
		this.name = name;
		this.numberValue = defaultValue;
		SmartDashboard.putNumber(name, defaultValue);
		type = Type.Number;
	}
	
	public Setting(String name, String defaultValue){
		this.name = name;
		this.stringValue = defaultValue;
		numberValue = 0;
		type = Type.String;
		SmartDashboard.putString(name, defaultValue);
	}
	
	public double getValue(){
		if(type == Type.Number){
			numberValue = SmartDashboard.getNumber(name, numberValue);
			return numberValue;
		}else{
			System.out.println("Error: this is not a number setting");
			return 0;
		}
		
	}
	
	public String getStringValue(){
		if(type == Type.String){
			stringValue = SmartDashboard.getString(name, stringValue);
			return stringValue;
		}else{
			System.out.println("Error: this is not a string setting");
			return "default string";
		}
	}
	
	
}

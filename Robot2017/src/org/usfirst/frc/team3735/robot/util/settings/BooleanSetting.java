package org.usfirst.frc.team3735.robot.util.settings;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BooleanSetting{
	
	private String name;
	private boolean value;
	
	private boolean isReceiving;
	private boolean isVisible;
	private boolean isListening = false;
	
	private static ArrayList<BooleanSetting> settings = new ArrayList<BooleanSetting>();
	private static int fIteration;
	
	public BooleanSetting(String name, boolean defaultValue){
		this.name = name;
		this.value = defaultValue;
		
		SmartDashboard.putBoolean(name, defaultValue);
		isVisible = true;
		isReceiving = true;
		settings.add(this);
	}
	
	//isListening is different from isReceiving because function onchange is called when a change is detected
	public void setIsListening(boolean val) {
		isListening = val;
		
	}

	
	/*
	 * @param flag	if true, it displays to the SmartDashboard but doesn't take input from it
	 * 				if false, the setting does not appear on the SmartDasboard
	 * 
	 */
	public BooleanSetting(String name, boolean defaultValue, boolean flag){
		this.name = name;
		this.value = defaultValue;
		
		if(flag){
			SmartDashboard.putBoolean(name, defaultValue);
			isVisible = true;
		}else{
			isVisible = false;
		}
		isReceiving = false;
	}

	
	public boolean getValue(){
		return value;
	}
	
	public boolean getValueFetched() {
		fetch();
		return value;
	}
	
	public void fetch() {
		if(isReceiving) {
			if(isListening) {
				if(value != SmartDashboard.getBoolean(name, value)) {
					value = SmartDashboard.getBoolean(name, value);
					valueChanged(value);
				}
			}else {
				value = SmartDashboard.getBoolean(name, value);
			}
		}
	}
	

	
	public void setValue(boolean value){
		this.value = value;
		if(isVisible){
			SmartDashboard.putBoolean(name, this.value);
		}
	}
	
	public static void fetchAll() {
		for(BooleanSetting s : settings) {
			s.fetch();
		}
	}
	
	/**
	 * This method is designed to be called every tick, and only fetches one setting at a time,
	 * which helps by not retrieving everything from the SDB at once every tick, or once every second.
	 * Number of Settings as of 6/29: 19. so every setting is updated every .4 seconds
	 */
	public static void fetchAround() {
		if(settings != null && !settings.isEmpty()) {
			fIteration++;
			if(fIteration >= settings.size()) {
				fIteration = 0;
			}
			settings.get(fIteration).fetch();
		}
	}
	
	//Override me if you want!
	public void valueChanged(boolean val) {
		
	}
	
}


package org.usfirst.frc.team3735.robot.util;

public class TrapProfile {
	private final double fps = 50;
	
	private double acc;
	private double cruiseVelocity;
	
	private double currentVelocity;
	
	//0 = accelerating, 1 = cruising, 2 = decelerating, 3 = finished
	private int stage;
	
	public TrapProfile(double v, double a){
		cruiseVelocity = v;
		acc = a/fps;
		currentVelocity = 0;
		stage = 0;
	}
	
	public void step(){
		if(stage == 0){
			currentVelocity += acc;
			if(currentVelocity >= cruiseVelocity){
				currentVelocity = cruiseVelocity;
				stage = 1;	//start cruising
			}
		}else if(stage == 2){
			currentVelocity -= acc;
			if(currentVelocity <= 0){
				currentVelocity = 0;
				stage = 3;	//its finished
			}
		}
	}
	
	public void startDeccelerating(){
		stage = 2;
	}
	
	public double getVelocity(){
		return currentVelocity;
	}
	
	public boolean isFinished(){
		return (stage == 3);
	}
	public boolean isCruising(){
		return (stage == 3);
	}
	
	
}

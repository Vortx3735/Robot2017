package org.usfirst.frc.team3735.robot.util.calc;

public class TrapProfile {
	private final double fps = 50;
	
	private double acc;
	private double cruiseSpeed;
	private double currentSpeed;
	
	//0 = accelerating, 1 = cruising, 2 = decelerating, 3 = finished
	private int stage;

	private double start;
	private double finish;
	private double direction = 1;
	private double accThresh = 0;
	
	public TrapProfile(double start, double finish, double v, double a){
		this.start = start;
		this.finish = finish;
		cruiseSpeed = v;
		acc = a/fps;
		currentSpeed = 0;
		stage = 0;
		if(finish < start){
			direction = -1;
		}
	}
	
	public void step(double input){
		if(stage == 0){
			currentSpeed += acc;
			if(currentSpeed >= cruiseSpeed){
				currentSpeed = cruiseSpeed;
				accThresh = input - start;
				stage = 1;	//start cruising
			}
		}else if(stage == 1){
			if(finish - input < accThresh){
				stage = 2;
			}
		}else if(stage == 2){
			currentSpeed -= acc;
			if(currentSpeed <= 0){
				currentSpeed = 0;
				stage = 3;	//its finished
			}
		}
	}
	
	public double getOutput(){
		return speedToOutput(currentSpeed) * direction;
	}
	
	public boolean isFinished(){
		return (stage == 3);
	}
	public boolean isCruising(){
		return (stage == 1);
	}
	
	//Override Me!!!
	public double speedToOutput(double v){
		return v;
	}
	
}

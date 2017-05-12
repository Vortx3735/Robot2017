package org.usfirst.frc.team3735.robot.triggers;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.util.VortxMath;
import org.usfirst.frc.team3735.robot.util.cmds.ComTrigger;

public class HasReachedAngle extends ComTrigger{
	
	private Double targetAngle;
	private double startAngle;
	private Double deltaAngle;

	public HasReachedAngle(Double angle){
		this.targetAngle = angle;
	}
	
	public HasReachedAngle(double angle){
		this(new Double(angle));
	}
	
	public HasReachedAngle(Double angle, boolean flag){
		this.deltaAngle = angle;
	}
	
	public HasReachedAngle(double angle, boolean flag){
		this(new Double(angle), flag);
	}

	@Override
	public void initialize() {
		startAngle = Robot.navigation.getYaw();
		deltaAngle = degreesToGo();
	}
	
	@Override
	public boolean get() {
		if(deltaAngle > 0){
			return degreesToGo() < 0;
		}else{
			return degreesToGo() > 0;
		}
	}

	public double degreesTraveled(){
		return VortxMath.continuousLimit(Robot.navigation.getYaw() - startAngle, -180, 180);
	}
	
	public double degreesToGo(){
		return VortxMath.continuousLimit(targetAngle - Robot.navigation.getYaw(), -180, 180);
	}
	
	public boolean isWtihinThresh(double d){
		return Math.abs(degreesToGo()) < d;
	}
	
	
}

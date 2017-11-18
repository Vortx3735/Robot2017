package org.usfirst.frc.team3735.robot.triggers;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.util.calc.VortxMath;
import org.usfirst.frc.team3735.robot.util.cmds.ComTrigger;

public class HasReachedAngle extends ComTrigger{
	
	private Double targetAngle;
	private double startAngle;
	private Double deltaAngle;
	private boolean isRelative;

	public HasReachedAngle(Double angle){
		this(angle, false);
	}
	
	public HasReachedAngle(double angle){
		this(new Double(angle));
	}
	
	public HasReachedAngle(Double angle, boolean isRelative){
		this.isRelative = isRelative;
		if(isRelative){
			this.deltaAngle = angle;
		}else{
			this.targetAngle = angle;
		}

	}
	
	public HasReachedAngle(double angle, boolean isRelative){
		this(new Double(angle), isRelative);
	}

	@Override
	public void initialize() {
		startAngle = Robot.navigation.getYaw();
		if(isRelative){
			targetAngle = limit(startAngle + deltaAngle);
		}else{
			deltaAngle = degreesToGo();
		}
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
		return limit(Robot.navigation.getYaw() - startAngle);
	}
	
	public double degreesToGo(){
		return limit(targetAngle - Robot.navigation.getYaw());
	}
	
	public double limit(double a){
		return VortxMath.continuousLimit(a, -180, 180);
	}
	
	public void setTargetAngle(double angle){
		targetAngle = limit(angle);
	}
	
	public boolean isRelative(){
		return isRelative;
	}
	
	@Override
	public String getHaltMessage() {
		return "Reached an Angle";
	}
	
	
}

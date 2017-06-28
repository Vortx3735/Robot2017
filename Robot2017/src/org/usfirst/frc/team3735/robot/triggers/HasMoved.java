package org.usfirst.frc.team3735.robot.triggers;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.util.cmds.ComTrigger;

public class HasMoved extends ComTrigger{
	
	private Double deltaDistance;
	private double rsd;
	private double lsd;

	public HasMoved(Double distance){
		this.deltaDistance = distance;
	}
	
	public HasMoved(double distance){
		this(new Double(distance));
	}

	@Override
	public void initialize() {
		lsd = Robot.drive.getLeftPositionInches();
		rsd = Robot.drive.getRightPositionInches();

	}
	
	@Override
	public boolean get() {
		if(deltaDistance > 0){
			return distanceTraveled() > deltaDistance.doubleValue();
		}else{
			return distanceTraveled() < deltaDistance.doubleValue();
		}
	}


	
	public double distanceTraveled(){
		return .5 * ((Robot.drive.getLeftPositionInches() - lsd) + (Robot.drive.getRightPositionInches() - rsd));
	}
	
	public double distanceToGo(){
		return deltaDistance - distanceTraveled();
	}
	
	public double distance(){
		return deltaDistance.doubleValue();
	}
	
	
}

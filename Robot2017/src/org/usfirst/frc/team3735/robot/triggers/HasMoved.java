package org.usfirst.frc.team3735.robot.triggers;

import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.util.cmds.ComTrigger;
import org.usfirst.frc.team3735.robot.util.settings.Func;

public class HasMoved extends ComTrigger{
	
	private Func deltaDistance;
	private double rsd;
	private double lsd;

	public HasMoved(Func distance){
		this.deltaDistance = distance;
	}
	
	public HasMoved(double distance){
		this(new Func() {
			public double getValue() {
				return distance;
			}
		});
	}

	@Override
	public void initialize() {
		lsd = Robot.drive.getLeftPositionInches();
		rsd = Robot.drive.getRightPositionInches();

	}
	
	@Override
	public boolean get() {
		if(deltaDistance.getValue() > 0){
			return distanceTraveled() > deltaDistance.getValue();
		}else{
			return distanceTraveled() < deltaDistance.getValue();
		}
	}


	
	public double distanceTraveled(){
		return .5 * ((Robot.drive.getLeftPositionInches() - lsd) + (Robot.drive.getRightPositionInches() - rsd));
	}
	
	public double distanceToGo(){
		return deltaDistance.getValue() - distanceTraveled();
	}
	
	public double distance(){
		return deltaDistance.getValue();
	}
	
	@Override
	public String getHaltMessage() {
		return "Moved " + deltaDistance + "Inches";
	}
	
	
}
